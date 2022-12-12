import React, { useRef, useState, useEffect } from "react";

import { Editor } from "slate-react";
import { initialValue } from "./slateIntialValue";
import io from "socket.io-client";

const socket = io("http://localhost:4000");

export default function SynchingEditor() {
  const [value, setValue] = useState(initialValue);
  const editor = useRef(null);
  const remote = useRef(false);

  // id for identifying which editor the client is on
  const id = useRef(`${Date.now()}`);

  useEffect(() => {
    socket.on("new-remote-operations", ({ editorId, ops }) => {
      if (id.current !== editorId) {
        remote.current = true;
        JSON.parse(ops).forEach((op) => editor.current.applyOperation(op));
        remote.current = false;
      }
    });
  }, []);

  return (
    <Editor
      ref={editor}
      value={value}
      onChange={(opts) => {
        setValue(opts.value);

        const ops = opts.operations
          .filter((o) => {
            if (o) {
              return (
                o.type !== "set_selection" &&
                o.type !== "set_value" &&
                (!o.data || !o.data.has("source"))
              );
            }

            return false;
          })
          .toJS()
          .map((o) => ({ ...o, data: { source: "one" } }));
        console.log(id.current);
        if (ops.length && !remote.current) {
          socket.emit("new-operations", {
            editorId: id.current,
            ops: JSON.stringify(ops),
          });
        }
      }}
    />
  );
}

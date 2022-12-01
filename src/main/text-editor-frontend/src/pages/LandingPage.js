import React from 'react';
import Button from '@material-ui/core/Button';


export default function LandingPage() {

    const handleClick = (page) => {
        console.log("button clicked " + page);
    }

    return( 
        <div>
            <Button variant="contained" color="primary" onClick={() => {
                handleClick("creaete new page")
            }}>Create New Page</Button>

            <Button variant="contained" color="success" onClick={() => {
                handleClick("join existing page")
            }}>Join Existing Page</Button>
    
        </div>)
}
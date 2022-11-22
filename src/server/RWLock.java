package server;

public class RWLock {

	private int readersCount = 0;
	private int writersCount = 0;
	private int writeRequestCount = 0;

	public synchronized void lockRead() throws InterruptedException{
		while(writersCount > 0 || writeRequestCount > 0){
			wait();
		}
		readersCount++;
	}

	public synchronized void unlockRead(){
		readersCount--;
		notifyAll();
	}

	public synchronized void lockWrite() throws InterruptedException {
		writeRequestCount++;

		while(readersCount > 0 || writersCount > 0){
			wait();
		}
		writeRequestCount--;
		writersCount++;
	}

	public synchronized void unlockWrite() throws InterruptedException {
		writersCount--;
		notifyAll();
	}

}

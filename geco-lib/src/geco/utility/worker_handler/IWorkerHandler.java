package geco.utility.worker_handler;

public interface IWorkerHandler 
{
	void addWorker		(Runnable p_Worker);
	void removeWorker	(Runnable p_Worker);
}

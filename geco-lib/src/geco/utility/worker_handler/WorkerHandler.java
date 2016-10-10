package geco.utility.worker_handler;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import geco.io.DataReceiver;



public class WorkerHandler extends Thread implements IWorkerHandler
{
	private static WorkerHandler g_Handler;
	
	public static void init(int p_NumThreads) throws Exception
		{
			if (WorkerHandler.g_Handler == null)
				WorkerHandler.g_Handler = new WorkerHandler(p_NumThreads);
		}
	
	public static WorkerHandler getInstance()
		{
			return WorkerHandler.g_Handler;
		}
	
	
	private ExecutorService 						m_ThreadPool;
	private CopyOnWriteArrayList<Runnable> 			m_Workers;
	private int										m_QueueSize;
	
	public WorkerHandler(int p_WorkerQueueSize)
	{
		super();
		this.m_ThreadPool 	= 	Executors.newFixedThreadPool(p_WorkerQueueSize);
		this.m_Workers		=	new CopyOnWriteArrayList<Runnable>();
		this.m_QueueSize	=	p_WorkerQueueSize;
		this.start();
	}
	
	@Override
	public void addWorker(Runnable p_Worker) 
	{
		if (!this.m_Workers.contains(p_Worker))
			this.m_Workers.add(p_Worker);
	}

	@Override
	public void removeWorker(Runnable p_Worker) 
	{
		if (this.m_Workers.contains(p_Worker))
			this.m_Workers.remove(p_Worker);
	}
	
	public void run()
	{
		while(true)
			{			
				this.m_ThreadPool = Executors.newFixedThreadPool(this.m_QueueSize);
				
				this.m_Workers.forEach((l_Worker ->{
					this.m_ThreadPool.execute(l_Worker);
				}));
				
				this.m_ThreadPool.shutdown();
				
				try 
					{
						while(!this.m_ThreadPool.isTerminated())
							this.m_ThreadPool.awaitTermination(1, TimeUnit.MILLISECONDS);
					} 
				catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				
				
			}
	}
	
	@Override
	protected void finalize() throws Throwable 
	{
		this.m_ThreadPool.shutdownNow();
		super.finalize();
	}

}

package com.darrylsite.ihm;

/**
 * parcourir les HM et mets à jours les IHM de presentation de données
 * @author nabster
 */
public class IHMUpdater implements Runnable
{
    private boolean terminate;

    public boolean isTerminate()
    {
        return terminate;
    }

    public void setTerminate(boolean terminate)
    {
        this.terminate = terminate;
    }

    @Override
    public void run()
    {
       initMessageDisplayers();
        
       while(! terminate)
       {
        pause(5000);
        if(IHMContext.getIhmReceiver() != null)
          IHMContext.getIhmReceiver().change();
        if(IHMContext.getIhmSent() != null)
          IHMContext.getIhmSent().change();
        if(IHMContext.getIhmToSent() != null)
          IHMContext.getIhmToSent().change();
        if(IHMContext.getIhmTrash() != null)
          IHMContext.getIhmTrash().change();
       }
    }

    private void pause(int p)
    {
        try {
            Thread.sleep(p);
        } catch (InterruptedException ex)
        {
        }
    }

    /**
     * Initiation des grilles d'affichage de message
     */
    public void initMessageDisplayers()
    {
     IHMContext.setIhmReceiver(new MessageReceiveIHM());
     IHMContext.setIhmSent(new MessageSentIHM());
     IHMContext.setIhmToSent(new MessageToSentIHM());
     IHMContext.setIhmTrash(new MessageTashIHM());
    }
    
}

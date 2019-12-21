package me.varmetek.plugin.prisonminerefill.editor;

import com.google.common.base.Preconditions;
import me.varmetek.plugin.prisonminerefill.MineRefillService;
import me.varmetek.plugin.prisonminerefill.editor.impl.StandardEditSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrisonMineEditor
{

  protected Map<UUID,PrisonMineEditSession> sessions;
  protected final MineRefillService SERVICE;

  public PrisonMineEditor(MineRefillService service){
    sessions = new HashMap<>();
    this.SERVICE = service;


  }


  /***
   *
   * Creates an edit session for the given user
   * The created session is returned
   * If the user already has a session, the session will be overriden
   *
   *
   * @rturns the created edit session
   * @throws NullPointerException if the given id is null
   * ***/
  public PrisonMineEditSession addEditSession(UUID id){
    checkId(id);
    PrisonMineEditSession  sess = new StandardEditSession(null);
    sessions.put(id, sess);
    return sess;
  }


  /**
   *
   * Tests to see if the given player has a a edit session
   * @return true if the edit session exists
   * @throws NullPointerException if the given id is null
   * */
  public boolean hasEditSession(UUID id){
    checkId(id);
    return sessions.containsKey(id);
  }

  /**
 * Removes the editsession from the given user id if a session exists
 * No error is thrown if the id does not have a session
 *
 * @throws NullPointerException if the given id is null
 */
  public void removeEditSession(UUID id){
    checkId(id);
      sessions.remove(id);
  }

  /**
   * Returns the edit session of the id if it exists or returns null
   * of the id if it exists
   *
   * @throws  NullPointerException if the given id is null;
   *
   * **/
  public PrisonMineEditSession getEditSession(UUID id){


      checkId(id);
      return sessions.get(id);

  }


  private void checkId(UUID id){
    Preconditions.checkNotNull(id,"Given id cannot be null");

  }
}

package com.cloudwebapp.server;


import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.cloudwebapp.client.PMF;
import com.cloudwebapp.client.service.AccountService;
import com.cloudwebapp.shared.AccountDTO;
import com.cloudwebapp.shared.MessageCode;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("unchecked")
public class AccountServiceImpl extends RemoteServiceServlet implements AccountService {
	private static final long serialVersionUID = 1L;
	
	@Override
	public int verify(String username, String password)
			throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Account get = (Account) pm.getObjectById(Account.class, username);
			if(get.getUsername().equals(username) && get.getPassword().equals(password)) {
				pm.close();
				if(get.isAvailable())
					return get.getType();
				else
					return MessageCode.VERIFY_FAILURE_UNAVAILABLE;
			}
			else 
				return MessageCode.VERIFY_FAILURE_WRONG_UN_PW;
		} catch(Exception e) {
			pm.close();
			return MessageCode.VERIFY_FAILURE_EXCEPTION_ERROR;
		}
	}

	@Override
	public int register(String username, String password, String sd, String n, String a, String d, String e) 
			throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Account newAccount;
		File root = new File(username, "root", null);
		if(username.equals("admin")) {
			// String author, String fileName, Long parent
			newAccount = new Account(username, username);
		}
		else {
			 newAccount = new Account(username, password, sd, n, a, d, e);
		}
		
		try {
			Query query = pm.newQuery("SELECT FROM " + Account.class.getName() + " WHERE username == " + "'" + username + "'");
			List<Account> result =  (List<Account>)query.execute();
			
			if(result.isEmpty()) {
				File rt = pm.makePersistent(root);
				newAccount.setRootId(rt.getId());
				
				pm.makePersistent(newAccount);
				pm.close();
				return MessageCode.REGISTER_SUCCESS;
			}
			else {
				pm.close();
				return MessageCode.REGISTER_FAILURE_USERNAME_EXISTED;
			}
		} catch(Exception exception) {
			pm.close();
			return MessageCode.REGISTER_FAILURE_EXCEPTION_ERROR;
		}
	}
	
	@Override
	public int updateAccount(AccountDTO account) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery("SELECT FROM " + Account.class.getName() + " WHERE username == " + "'" + account.getUsername() + "'");
			List<Account> result = (List<Account>) query.execute();
			if(result.isEmpty()) {
				pm.close();
				return MessageCode.ACCOUNT_NOT_FOUND;
			}
			else {
				Account get = result.get(0);
				get.updateValue(account.getUsername(), account.getPassword(), 
						account.getName(), account.getAddress(), account.getBirthDate(), account.getEmail());
				pm.close();
				return MessageCode.UPDATE_SUCCESS;
			}
		} catch(Exception exception) {
			pm.close();
			return MessageCode.UPDATE_FAILURE_EXCEPTION_ERROR;
		}	
	}
	
	@Override
	public int blockOrFreeAccount(String username, boolean available) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery("SELECT FROM " + Account.class.getName() + " WHERE username == " + "'" + username + "'");
			List<Account> result = (List<Account>) query.execute();
			if(result.isEmpty()) {
				pm.close();
				return MessageCode.ACCOUNT_NOT_FOUND;
			}
			else {
				Account get = result.get(0);
				if(available) {
					get.setFree();
					pm.close();
					return MessageCode.AVAILABLE_SUCCESS_FREE;
				}
				else {
					get.setBlock();
					pm.close();
					return MessageCode.AVAILABLE_SUCCESS_BLOCK;
				}
			}
		} catch(Exception exception) {
			pm.close();
			return MessageCode.AVAILABLE_FAILURE_EXCEPTION_ERROR;
		}
	}
	
	@Override
	public int deleteAccount(String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery("SELECT FROM " + Account.class.getName() + " WHERE username == " + "'" + username + "'");
			List<Account> result = (List<Account>) query.execute();
			if(result.isEmpty()) {
				pm.close();
				return MessageCode.ACCOUNT_NOT_FOUND;
			}
			else {
				Account get = result.get(0);
				pm.deletePersistent(get);
				pm.close();
				return MessageCode.DELETE_SUCCESS;
			}
		} catch(Exception exception) {
			pm.close();
			return MessageCode.DELETE_FAILURE_EXCEPTION_ERROR;
		}
	}

	@Override
	public AccountDTO getAccount(String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery("SELECT FROM " + Account.class.getName() + " WHERE username == " + "'" + username + "'");
			List<Account> result = (List<Account>) query.execute();
			if(result.isEmpty()) {
				pm.close();
				return null;
			}
			else {
				pm.close();
				Account get = result.get(0);
				AccountDTO ac = new AccountDTO();
				ac.setValue(get.getUsername(), get.getPassword(), get.getSignUpDate(),
							get.getName(), get.getAddress(), get.getBirthDate(), get.getEmail(), get.getType(), get.isAvailable());
				ac.setRootId(get.getRootId());
				return ac;
			}
		} catch(Exception exception) {
			pm.close();
			return null;
		}	
	}

	@Override
	public ArrayList<AccountDTO> getAccountList() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery("SELECT FROM " + Account.class.getName());
			query.setOrdering("type ascending");
			List<Account> result = (List<Account>) query.execute();
			if(result.isEmpty()) {
				pm.close();
				return null;
			}
			else {
				pm.close();
				ArrayList<AccountDTO> list = new ArrayList<AccountDTO>();
				for(Account account : result) {
					AccountDTO ac = new AccountDTO();
					ac.setValue(account.getUsername(), account.getPassword(), account.getSignUpDate(),
							account.getName(), account.getAddress(), account.getBirthDate(), account.getEmail(), account.getType(), account.isAvailable());
					list.add(ac);
				}
				return list;
			}
		} catch(Exception exception) {
			pm.close();
			return null;
		}	
	}
}

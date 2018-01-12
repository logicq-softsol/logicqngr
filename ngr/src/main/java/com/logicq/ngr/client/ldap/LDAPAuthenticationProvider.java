package com.logicq.ngr.client.ldap;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPAuthenticationProvider {

	static String ldapBaseIC = "CN=DYNSDEV,OU=functional,OU=btplc,DC=iuser,DC=iroot,DC=adidom,DC=com";
	static String ldapUrl = "ldap://iuserldap.nat.bt.com:389";
	static String credentials = "S0mething@123";
	static String deldapeinBase = "OU=employee,OU=btplc,DC=iuser,DC=iroot,DC=adidom,DC=com";
	static String qryName = "DL";
	static String ldapFilter = "(&(objectCategory=user)(mailNickName=<EIN>))";
	static String qryType = "BT_USERDETAILS";

	private DirContext dirContext;

	public LDAPAuthenticationProvider() throws Exception {
		this.dirContext = initContext(ldapBaseIC, ldapUrl, credentials);
	}

	private DirContext initContext(String ldapBase, String ldapUrl, String credentail) throws Exception {

		Properties env = new Properties();
		DirContext dirContext = null;

		env.put(DirContext.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");

		env.put(Context.SECURITY_PRINCIPAL, ldapBase);
		env.put(DirContext.PROVIDER_URL, ldapUrl);
		env.put(Context.SECURITY_CREDENTIALS, credentail);

		dirContext = new InitialDirContext(env);
		if (dirContext != null) {

		}

		return dirContext;
	}

	public boolean authenticateUser(String ein, String pwd) throws Exception {
		DirContext dirContext = initContext("CN=" + ein + "," + deldapeinBase, ldapUrl, pwd);
		if (dirContext != null) {
			return true;
		}
		return false;
	}

	public void CloseLDAPContext() throws Exception {
		if (this.dirContext != null)
			this.dirContext.close();
	}

	public String validateUser(String userName) throws Exception {
		if (null != dirContext) {
			SearchControls sc = new SearchControls();
			NamingEnumeration<SearchResult> results = null;

			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
			results = dirContext.search(deldapeinBase, ldapFilter.replace("<EIN>", userName), sc);

			if (results == null || !results.hasMore()) {
				return null;
			}
			while (results.hasMore()) {
				SearchResult sr = (SearchResult) results.next();
				String einStr = sr.getName();
				return einStr.substring(einStr.indexOf("CN=") + 3);
			}
		}
		return null;
	}

}

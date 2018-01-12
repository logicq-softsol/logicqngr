package com.logicq.ngr.dao.impl;

import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.MessageDao;
import com.logicq.ngr.model.Message;

@Repository
public class MessageDaoImpl extends AbstractDAO<Message> implements MessageDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4364916123038960299L;

	@Override
	public Message getMessage(int code) {
		return getRecordById(Message.class, code);
	}
}
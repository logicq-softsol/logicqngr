package com.logicq.ngr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.MessageDao;
import com.logicq.ngr.model.Message;
import com.logicq.ngr.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageDao messageDao;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public Message getMessage(int code) {
		return messageDao.getMessage(code);
	}
}
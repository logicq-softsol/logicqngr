package com.logicq.ngr.dao;

import com.logicq.ngr.model.Message;

public interface MessageDao {
	Message getMessage(int code);
}
package com.logicq.ngr.common.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logicq.ngr.model.Message;
import com.logicq.ngr.service.MessageService;
import com.logicq.ngr.vo.MessageVO;

@Component
public class MessageHelper {

	@Autowired
	MessageService messageService;

	public MessageVO handleBusinessMessage(int messageCode) {
		MessageVO messageVO = new MessageVO();
		Message message = messageService.getMessage(messageCode);
		messageVO.setMessagecode(message.getCode());
		messageVO.setMessage(message.getMessage());
		return messageVO;
	}
}

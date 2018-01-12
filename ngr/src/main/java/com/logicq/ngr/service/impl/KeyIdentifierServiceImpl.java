package com.logicq.ngr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.KeyIdentifierDao;
import com.logicq.ngr.model.KeyIdentifier;
import com.logicq.ngr.service.KeyIdentifierService;

@Service
public class KeyIdentifierServiceImpl implements KeyIdentifierService {

	@Autowired
	KeyIdentifierDao keyIdentifierDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KeyIdentifier> getKeyIdentifiers() {
		return keyIdentifierDao.getKeyIdentifiers();
	}

}

package com.goldfinance.jinGC.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.goldfinance.jinGC.mapper.CertificateTypeMapper;
import com.goldfinance.jinGC.po.CertificateType;
import com.goldfinance.jinGC.service.CertificateTypeService;

public class CertificateTypeServiceImpl implements CertificateTypeService {
	
	@Autowired
	private CertificateTypeMapper certificateTypeMapper;

	@Override
	public List<CertificateType> findCertificateTypeList() throws Exception {
		List<CertificateType> certificateTypeList = certificateTypeMapper.findCertificateTypeList();
		return certificateTypeList;
	}

}

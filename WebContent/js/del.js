function chkBrnch(id) {
	if (gtAnsFFinp('OpManOv', '', '', '', '') > "8") {
		if (chkFNZPosInt('SLAP')) {
			setAnsFFInp('OpMeasrScore', "2");
		} else {
			if ((chkFNZPosInt('SLAM') || chkFNZPosInt('MIS'))) {
				setAnsFFInp('OpMeasrScore', "5");
			} else {
				setAnsFFInp('OpMeasrScore', "8");
			}
		}
	} else {
		setAnsFFInp('OpMeasrScore', gtAnsFFinp('OpManOv', '', '', '', ''));
	}
	if (!(hasAns(gtAnsFFinp('OpP', '', '', '', ''), gtAnsFFinp('OpMeasrScore',
			'', '', '', '')))) {
		writit(rygLabelMap[gtAnsFFinp('OpMeasrScore')], 'OpMeasrCurrIcon');
		writit(rygLabelMap[gtAnsFFinp('OpMeasrScore')], 'OpMeasrCurrIcon_1');
		setBgClr('OpMeasrCurrStatusField',
				rygHashtableHist[gtAnsFFinp('OpMeasrScore')]);
		setBgClr('OpMeasrCurrStatusField_1',
				rygHashtableHist[gtAnsFFinp('OpMeasrScore')]);
		setAnsFFInp('OpP', gtAnsFFinp('OpMeasrScore', '', '', '', ''));
	} else {
	}
	if (hasAns(gtAnsFFinp('_REF1_isBlueAccount', '', '', '', ''), "true")) {
		setAnsFFInp('OverallScore', "10");
	} else {
		setAnsFFInp('OverallScore', min(gtAnsFFinp('BizCtrlScore', '', '', '',
				''), gtAnsFFinp('CustSatScore', '', '', '', ''), gtAnsFFinp(
				'FinPerfScore', '', '', '', ''), gtAnsFFinp('OpMeasrScore', '',
				'', '', '')));
	}
	setAnsFFInp('AMscr', min(gtAnsFFinp('AMscr2', '', '', '', ''), gtAnsFFinp(
			'AMscr1', '', '', '', '')));
	setBgClr('CompAM_cellid', rygHashtableCurr[gtAnsFFinp('AMscr2')]);
	setBgClr('CompBAS_cellid', rygHashtableCurr[gtAnsFFinp('BAscr2')]);
	setBgClr('CompEUS_cellid', rygHashtableCurr[gtAnsFFinp('EUSscr2')]);
	setBgClr('CompNSD_cellid', rygHashtableCurr[gtAnsFFinp('NSscr2')]);
	setBgClr('CompSRM_cellid', rygHashtableCurr[gtAnsFFinp('SRMscr2')]);
	setBgClr('CompSSO_cellid', rygHashtableCurr[gtAnsFFinp('SSOscr2')]);
	setBgClr('CompTIM_cellid', rygHashtableCurr[gtAnsFFinp('TIMscr2')]);
	setBgClr('CompSM_cellid', rygHashtableCurr[gtAnsFFinp('SMscr2')]);
	setBgClr('CompDTE_cellid', rygHashtableCurr[gtAnsFFinp('DTEscr2')]);
	setBgClr('CompGCMS_cellid', rygHashtableCurr[gtAnsFFinp('GCMSscr2')]);
	setBgClr('CompNITD_cellid', rygHashtableCurr[gtAnsFFinp('NITDscr2')]);
	setBgClr('CompXC_cellid', rygHashtableCurr[gtAnsFFinp('XCscr2')]);
	setAnsFFInp('BAscr', min(gtAnsFFinp('BAscr2', '', '', '', ''), gtAnsFFinp(
			'BAscr1', '', '', '', '')));
	setAnsFFInp('EUSscr', min(gtAnsFFinp('EUSscr2', '', '', '', ''),
			gtAnsFFinp('EUSscr1', '', '', '', '')));
	setAnsFFInp('NSscr', min(gtAnsFFinp('NSscr2', '', '', '', ''), gtAnsFFinp(
			'NSscr1', '', '', '', '')));
	setAnsFFInp('SRMscr', min(gtAnsFFinp('SRMscr2', '', '', '', ''),
			gtAnsFFinp('SRMscr1', '', '', '', '')));
	setAnsFFInp('SMscr', min(gtAnsFFinp('SMscr2', '', '', '', ''), gtAnsFFinp(
			'SMscr1', '', '', '', '')));
	setAnsFFInp('SSOscr', min(gtAnsFFinp('SSOscr2', '', '', '', ''),
			gtAnsFFinp('SSOscr1', '', '', '', '')));
	setAnsFFInp('TIMscr', min(gtAnsFFinp('TIMscr2', '', '', '', ''),
			gtAnsFFinp('TIMscr1', '', '', '', '')));
	setAnsFFInp('XCscr', min(gtAnsFFinp('XCscr2', '', '', '', ''), gtAnsFFinp(
			'XCscr1', '', '', '', '')));
	setAnsFFInp('DTEscr', min(gtAnsFFinp('DTEscr2', '', '', '', ''),
			gtAnsFFinp('DTEscr1', '', '', '', '')));
	setAnsFFInp('GCMSscr', min(gtAnsFFinp('GCMSscr2', '', '', '', ''),
			gtAnsFFinp('GCMSscr1', '', '', '', '')));
	setAnsFFInp('NITDscr', min(gtAnsFFinp('NITDscr2', '', '', '', ''),
			gtAnsFFinp('NITDscr1', '', '', '', '')));
	if (hasAns(gtAnsFFinp('overAllOPM', '', '', '', ''), "update")) {
		show('OpMeasrUpdateBanner');
		show('OpMeasrManualOverride');
		if (hasAns(gtAnsFFinp('ommo', '', '', '', ''), "manualOverride")) {
			setAnsFFInp('OpManOv', gtAnsFFinp('omManScore', '', '', '', ''));
			show('omMan');
			show('omOvrRd');
			writit(gtAnsFFinp('star', '', '', '', ''), 'OMStar');
		} else {
			clrQues('omManExp');
			setAnsFFInp('OpManOv', "9");
			hide('omMan');
			hide('omOvrRd');
			writit(gtAnsFFinp('blank', '', '', '', ''), 'OMStar');
		}
		show('OpMeasrBlk');
		if (hasAns(gtAnsFFinp('SLOonly', '', '', '', ''), "expand")) {
			show('SLOBlk');
		} else {
			hide('SLOBlk');
		}
		show('SLOTotals');
		show('MI');
		if (hasAns(gtAnsFFinp('MIonly', '', '', '', ''), "expand")) {
			show('MIBlk');
		} else {
			hide('MIBlk');
		}
		show('MITotals');
		if ((hasAns(gtAnsFFinp('AccountGEO', '', '', '', ''), "Japan")
				|| hasAns(gtAnsFFinp('AccountGEO', '', '', '', ''), "EUR")
				|| hasAns(gtAnsFFinp('AccountIOT', '', '', '', ''), "MEA") || hasAns(
				gtAnsFFinp('rcaEnabled', '', '', '', ''), "true"))) {
			show('RCAOpen');
			show('RCATotals');
			if (hasAns(gtAnsFFinp('RCAonly', '', '', '', ''), "expand")) {
				show('RCAdetail');
				show('RCASLASummary');
			} else {
				hide('RCAdetail');
				hide('RCASLASummary');
			}
		} else {
		}
	} else {
		hide('OpMeasrUpdateBanner');
		hide('OpMeasrManualOverride');
		hide('omOvrRd');
		hide('OpMeasrBlk');
		hide('SLOBlk');
		hide('MIBlk');
		hide('MI');
		hide('RCAOpen');
		hide('RCASLASummary');
		hide('RCAdetail');
		hide('SLOTotals');
		hide('MITotals');
		hide('RCATotals');
	}
}
function gtAnsFFinp(id, type, format, sub, sType) {
	var q = elementArray[id];
	if (q == null) {
		return "";
	}
	if (type == 'date') {
		return getDateAns(q, format, sub, sType, id);
	}
	var t = "";
	if (q.type == "select-one" || q.type == "select-multiple") {
		t = q.type;
	} else if (q[0]) {
		t = q[0].type;
	} else {
		t = q.type;
	}
	switch (t) {
	case ("radio"):
		return getRadioAnswer(q);
	case ("checkbox"):
		return getCheckboxAnswer(q);
	case ("select-one"):
		return getSelectOneAnswer(q);
	case ("select-multiple"):
		return getSelectMultipleAnswer(q);
	case ("text"):
		return getTextAnswer(q, type);
	case ("textarea"):
		return getTextAreaAnswer(q);
	case ("hidden"):
		return q.value;
	default:
		return "";
	}
}
function chkBrnch(id) {
	if (gtAnsFFinp('OpManOv', '', '', '', '') > "8") {
		if (chkFNZPosInt('SLAP')) {
			setAnsFFInp('OpMeasrScore', "2");
		} else {
			if ((chkFNZPosInt('SLAM') || chkFNZPosInt('MIS'))) {
				setAnsFFInp('OpMeasrScore', "5");
			} else {
				setAnsFFInp('OpMeasrScore', "8");
			}
		}
	} else {
		setAnsFFInp('OpMeasrScore', gtAnsFFinp('OpManOv', '', '', '', ''));
	}
	if (!(hasAns(gtAnsFFinp('OpP', '', '', '', ''), gtAnsFFinp('OpMeasrScore',
			'', '', '', '')))) {
		writit(rygLabelMap[gtAnsFFinp('OpMeasrScore')], 'OpMeasrCurrIcon');
		writit(rygLabelMap[gtAnsFFinp('OpMeasrScore')], 'OpMeasrCurrIcon_1');
		setBgClr('OpMeasrCurrStatusField',
				rygHashtableHist[gtAnsFFinp('OpMeasrScore')]);
		setBgClr('OpMeasrCurrStatusField_1',
				rygHashtableHist[gtAnsFFinp('OpMeasrScore')]);
		setAnsFFInp('OpP', gtAnsFFinp('OpMeasrScore', '', '', '', ''));
	} else {
	}
	if (hasAns(gtAnsFFinp('_REF1_isBlueAccount', '', '', '', ''), "true")) {
		setAnsFFInp('OverallScore', "10");
	} else {
		setAnsFFInp('OverallScore', min(gtAnsFFinp('BizCtrlScore', '', '', '',
				''), gtAnsFFinp('CustSatScore', '', '', '', ''), gtAnsFFinp(
				'FinPerfScore', '', '', '', ''), gtAnsFFinp('OpMeasrScore', '',
				'', '', '')));
	}
	setAnsFFInp('AMscr', min(gtAnsFFinp('AMscr2', '', '', '', ''), gtAnsFFinp(
			'AMscr1', '', '', '', '')));
	setBgClr('CompAM_cellid', rygHashtableCurr[gtAnsFFinp('AMscr2')]);
	setBgClr('CompBAS_cellid', rygHashtableCurr[gtAnsFFinp('BAscr2')]);
	setBgClr('CompEUS_cellid', rygHashtableCurr[gtAnsFFinp('EUSscr2')]);
	setBgClr('CompNSD_cellid', rygHashtableCurr[gtAnsFFinp('NSscr2')]);
	setBgClr('CompSRM_cellid', rygHashtableCurr[gtAnsFFinp('SRMscr2')]);
	setBgClr('CompSSO_cellid', rygHashtableCurr[gtAnsFFinp('SSOscr2')]);
	setBgClr('CompTIM_cellid', rygHashtableCurr[gtAnsFFinp('TIMscr2')]);
	setBgClr('CompSM_cellid', rygHashtableCurr[gtAnsFFinp('SMscr2')]);
	setBgClr('CompDTE_cellid', rygHashtableCurr[gtAnsFFinp('DTEscr2')]);
	setBgClr('CompGCMS_cellid', rygHashtableCurr[gtAnsFFinp('GCMSscr2')]);
	setBgClr('CompNITD_cellid', rygHashtableCurr[gtAnsFFinp('NITDscr2')]);
	setBgClr('CompXC_cellid', rygHashtableCurr[gtAnsFFinp('XCscr2')]);
	setAnsFFInp('BAscr', min(gtAnsFFinp('BAscr2', '', '', '', ''), gtAnsFFinp(
			'BAscr1', '', '', '', '')));
	setAnsFFInp('EUSscr', min(gtAnsFFinp('EUSscr2', '', '', '', ''),
			gtAnsFFinp('EUSscr1', '', '', '', '')));
	setAnsFFInp('NSscr', min(gtAnsFFinp('NSscr2', '', '', '', ''), gtAnsFFinp(
			'NSscr1', '', '', '', '')));
	setAnsFFInp('SRMscr', min(gtAnsFFinp('SRMscr2', '', '', '', ''),
			gtAnsFFinp('SRMscr1', '', '', '', '')));
	setAnsFFInp('SMscr', min(gtAnsFFinp('SMscr2', '', '', '', ''), gtAnsFFinp(
			'SMscr1', '', '', '', '')));
	setAnsFFInp('SSOscr', min(gtAnsFFinp('SSOscr2', '', '', '', ''),
			gtAnsFFinp('SSOscr1', '', '', '', '')));
	setAnsFFInp('TIMscr', min(gtAnsFFinp('TIMscr2', '', '', '', ''),
			gtAnsFFinp('TIMscr1', '', '', '', '')));
	setAnsFFInp('XCscr', min(gtAnsFFinp('XCscr2', '', '', '', ''), gtAnsFFinp(
			'XCscr1', '', '', '', '')));
	setAnsFFInp('DTEscr', min(gtAnsFFinp('DTEscr2', '', '', '', ''),
			gtAnsFFinp('DTEscr1', '', '', '', '')));
	setAnsFFInp('GCMSscr', min(gtAnsFFinp('GCMSscr2', '', '', '', ''),
			gtAnsFFinp('GCMSscr1', '', '', '', '')));
	setAnsFFInp('NITDscr', min(gtAnsFFinp('NITDscr2', '', '', '', ''),
			gtAnsFFinp('NITDscr1', '', '', '', '')));
	if (hasAns(gtAnsFFinp('overAllOPM', '', '', '', ''), "update")) {
		show('OpMeasrUpdateBanner');
		show('OpMeasrManualOverride');
		if (hasAns(gtAnsFFinp('ommo', '', '', '', ''), "manualOverride")) {
			setAnsFFInp('OpManOv', gtAnsFFinp('omManScore', '', '', '', ''));
			show('omMan');
			show('omOvrRd');
			writit(gtAnsFFinp('star', '', '', '', ''), 'OMStar');
		} else {
			clrQues('omManExp');
			setAnsFFInp('OpManOv', "9");
			hide('omMan');
			hide('omOvrRd');
			writit(gtAnsFFinp('blank', '', '', '', ''), 'OMStar');
		}
		show('OpMeasrBlk');
		if (hasAns(gtAnsFFinp('SLOonly', '', '', '', ''), "expand")) {
			show('SLOBlk');
		} else {
			hide('SLOBlk');
		}
		show('SLOTotals');
		show('MI');
		if (hasAns(gtAnsFFinp('MIonly', '', '', '', ''), "expand")) {
			show('MIBlk');
		} else {
			hide('MIBlk');
		}
		show('MITotals');
		if ((hasAns(gtAnsFFinp('AccountGEO', '', '', '', ''), "Japan")
				|| hasAns(gtAnsFFinp('AccountGEO', '', '', '', ''), "EUR")
				|| hasAns(gtAnsFFinp('AccountIOT', '', '', '', ''), "MEA") || hasAns(
				gtAnsFFinp('rcaEnabled', '', '', '', ''), "true"))) {
			show('RCAOpen');
			show('RCATotals');
			if (hasAns(gtAnsFFinp('RCAonly', '', '', '', ''), "expand")) {
				show('RCAdetail');
				show('RCASLASummary');
			} else {
				hide('RCAdetail');
				hide('RCASLASummary');
			}
		} else {
		}
	} else {
		hide('OpMeasrUpdateBanner');
		hide('OpMeasrManualOverride');
		hide('omOvrRd');
		hide('OpMeasrBlk');
		hide('SLOBlk');
		hide('MIBlk');
		hide('MI');
		hide('RCAOpen');
		hide('RCASLASummary');
		hide('RCAdetail');
		hide('SLOTotals');
		hide('MITotals');
		hide('RCATotals');
	}
}

function createBlocks() {
	var tblock0 = [ 'Serv_CIS', 'Serv_CSC', 'Serv_DCS', 'Serv_NeM', 'Serv_SMD',
			'Serv_SDM' ];
	blocks['Serv_rows'] = tblock0;
	blockVisible['Serv_rows'] = false;
	var tblock1 = [ 'CompEUS1', 'CompNS1', 'CompSM1', 'CompSSO1', 'CompEUS2',
			'CompNS2', 'CompSM2', 'CompSSO2', 'CompEUS3', 'CompNS3', 'CompSM3',
			'CompSSO3', 'CompEUS4', 'CompNS4', 'CompSM4', 'CompSSO4',
			'CompEUS5', 'CompNS5', 'CompSM5', 'CompSSO5', 'CompEUS6',
			'CompNS6', 'CompSM6', 'CompSSO6' ];
	blocks['CompeHist_rows'] = tblock1;
	blockVisible['CompeHist_rows'] = false;
	var tblock2 = [ 'CompEUS_row', 'CompNSD_row', 'CompSM_row', 'CompSSO_row' ];
	blocks['Comp_rows'] = tblock2;
	blockVisible['Comp_rows'] = false;
	var tblock3 = [ 'SLApen', 'SLA_CSC', 'SLA_DCS', 'SLA_NeM', 'SLA_SMD',
			'SLA_SDM', 'SLATotals', 'SLOpen' ];
	blocks['OpMeasrBlk'] = tblock3;
	blockVisible['OpMeasrBlk'] = false;
	var tblock4 = [ 'SLO_CIS', 'SLO_CSC', 'SLO_DCS', 'SLO_NeM', 'SLO_SMD',
			'SLO_SDM', 'SLOTotals', 'MI' ];
	blocks['SLOBlk'] = tblock4;
	blockVisible['SLOBlk'] = false;
	var tblock5 = [ 'MIrow_CIS', 'MIrow_CSC', 'MIrow_DCS', 'MIrow_NeM',
			'MIrow_SMD', 'MIrow_SDM', 'MITotals' ];
	blocks['MIBlk'] = tblock5;
	blockVisible['MIBlk'] = false;
}
/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestPlayerResp.java
 * Date 2013-03-07
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TQtiTestPlayerResp.
 * 
 * @author S.Schwarzrock
 *
 */
public class TQtiTestPlayerResp implements IClixMappingClass {

	private TQtiTestPlayerRespPK id;
	private Long candidate;
	private Long container;
	private Long content;
	private Long response;
	private Long subitemPosition;
	private Long testItem;
	private String evaluationDate;
	private Double evaluatedScore;
	private Long itemResultStatus;
	private Long itemProcessStatus;
	private Long processStatus;
	private String text;
	
	public TQtiTestPlayerRespPK getId() {
		return id;
	}
	
	public void setId(TQtiTestPlayerRespPK id) {
		this.id = id;
	}
	
	public Long getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Long candidate) {
		this.candidate = candidate;
	}
	
	public Long getContainer() {
		return container;
	}
	
	public void setContainer(Long container) {
		this.container = container;
	}
	
	public Long getContent() {
		return content;
	}
	
	public void setContent(Long content) {
		this.content = content;
	}
	
	public Long getResponse() {
		return response;
	}
	
	public void setResponse(Long response) {
		this.response = response;
	}
	
	public Long getSubitemPosition() {
		return subitemPosition;
	}
	
	public void setSubitemPosition(Long subitemPosition) {
		this.subitemPosition = subitemPosition;
	}
	
	public Long getTestItem() {
		return testItem;
	}
	
	public void setTestItem(Long testItem) {
		this.testItem = testItem;
	}

	public String getEvaluationDate() {
		return evaluationDate;
	}

	public void setEvaluationDate(String evaluationDate) {
		this.evaluationDate = evaluationDate;
	}

	public Double getEvaluatedScore() {
		return evaluatedScore;
	}

	public void setEvaluatedScore(Double evaluatedScore) {
		this.evaluatedScore = evaluatedScore;
	}

	public Long getItemResultStatus() {
		return itemResultStatus;
	}

	public void setItemResultStatus(Long itemResultStatus) {
		this.itemResultStatus = itemResultStatus;
	}

	public Long getItemProcessStatus() {
		return itemProcessStatus;
	}

	public void setItemProcessStatus(Long itemProcessStatus) {
		this.itemProcessStatus = itemProcessStatus;
	}
	
	public Long getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Long processStatus) {
		this.processStatus = processStatus;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}

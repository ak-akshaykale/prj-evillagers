package com.cdac.evillagers.master.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="EV_events")
public class EventPojo {
	
	@Id
	@GeneratedValue
	public int id;
	public String title;
	public LocalDateTime dateofPost;
	public Date venueDate;
	public String descstring;
}

package vn.hoangphat.dao.impl;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="videos")
@NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v")
public class Video implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="VideoId")
	private String videoId;
	
	@Column(name="Active")
	private int active;
	
	@Column(name="Description", columnDefinition = "NVARCHAR(500) NULL")
	private String description;
	
	@Column(name="Poster", columnDefinition = "NVARCHAR(500) NULL")
	private String images;
	
	@Column(name="Title", columnDefinition = "NVARCHAR(500) NULL")
	private String title;
	
	@Column(name="Views")
	private int views;
	
	@ManyToOne
	@JoinColumn(name="CategoryId")
	private Category category;
	
}

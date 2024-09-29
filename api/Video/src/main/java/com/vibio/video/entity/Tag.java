/*
 *  Tag
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:16 PM
 * */

package com.vibio.video.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tag {
	@Id
	@SequenceGenerator(name = "tag_id_sq", sequenceName = "tag_id_sq", initialValue = 10, allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_sq")
	private Long id;

	private String name;
}

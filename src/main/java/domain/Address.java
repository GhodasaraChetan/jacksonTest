package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ADDRESS")
public class Address {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_person", referencedColumnName = "id")
	@JsonIgnore
	private Person person;

	@Column(name = "ADDLINE1")
	private String addLine1;

	@Column(name = "ADDLINE2")
	private String addLine2;

	@Column(name = "ZIPCODE")
	private String zipcode;
}

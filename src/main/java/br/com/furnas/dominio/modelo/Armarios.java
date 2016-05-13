package br.com.furnas.dominio.modelo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
@Entity
@Table(name = "ARMARIOS")
public class Armarios extends EntidadeBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ARMARIO", insertable = false, updatable = false)
	private long identificador;

	@Column(name = "DESC_ARMARIO", nullable = false, length = 255)
	private String descricao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_CR_ARMARIO", nullable = false, columnDefinition="DATETIME")
	private Date dtCrl;

	@Column(name = "MAT_ADM_CR_ARMARIO", nullable = false, length = 50)
	private String matAdmCr;

	@Column(name = "R_OBJECT_ID", nullable = true, length = 50)
	private String objectId;

	@Column(name = "QTD_PASTAS_ARMARIO")
	private long qtdPastas;

	@Column(name = "QTD_PASTAS_ARMARIO")
	private long qtdDocumentos;

	@PrePersist
	protected void onCreate() {
		dtCrl = new Date();
		matAdmCr = recuperarUsuario().getUser();
	}

	@PreUpdate
	protected void onUpdate() {
		dtCrl = new Date();
		matAdmCr = recuperarUsuario().getUser();
	}

	public Armarios() {
		super();
	}

	public long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(long identificador) {
		this.identificador = identificador;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDtCrl() {
		return dtCrl;
	}

	public void setDtCrl(Date dtCrl) {
		this.dtCrl = dtCrl;
	}

	public String getMatAdmCr() {
		return matAdmCr;
	}

	public void setMatAdmCr(String matAdmCr) {
		this.matAdmCr = matAdmCr;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public long getQtdPastas() {
		return qtdPastas;
	}

	public void setQtdPastas(long qtdPastas) {
		this.qtdPastas = qtdPastas;
	}

	public long getQtdDocumentos() {
		return qtdDocumentos;
	}

	public void setQtdDocumentos(long qtdDocumentos) {
		this.qtdDocumentos = qtdDocumentos;
	}
}

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
@Table(name = "REPOSITORIOS")
public class Repositorio extends EntidadeBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_REPOSITORIO", insertable = false, updatable = false)
	private long identificador;

	@Column(name = "DESC_REPOSITORIO", nullable = false, length = 255)
	private String descricaoDoRepositorio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_CR_REPOSITORIO", nullable = false, columnDefinition="DATETIME")
	private Date dtCrlRepositorio;

	@Column(name = "MAT_ADM_CR_REPOSITORIO", nullable = false, length = 50)
	private String matAdmCrRepositorio;

	@Column(name = "R_OBJECT_ID", nullable = true, length = 50)
	private String objectId;

	@Column(name = "QTD_PASTAS_REPOSITORIO")
	private long qtdPastasRepositorio;

	@Column(name = "QTD_PASTAS_REPOSITORIO")
	private long qtdDocumentosRepositorio;

	@PrePersist
	protected void onCreate() {
		dtCrlRepositorio = new Date();
		matAdmCrRepositorio = recuperarUsuario().getUser();
	}

	@PreUpdate
	protected void onUpdate() {
		dtCrlRepositorio = new Date();
		matAdmCrRepositorio = recuperarUsuario().getUser();
	}

	public Repositorio() {
		super();
	}

	public long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(long identificador) {
		this.identificador = identificador;
	}

	public String getDescricaoDoRepositorio() {
		return descricaoDoRepositorio;
	}

	public void setDescricaoDoRepositorio(String descricaoDoRepositorio) {
		this.descricaoDoRepositorio = descricaoDoRepositorio;
	}

	public Date getDtCrlRepositorio() {
		return dtCrlRepositorio;
	}

	public void setDtCrlRepositorio(Date dtCrlRepositorio) {
		this.dtCrlRepositorio = dtCrlRepositorio;
	}

	public String getMatAdmCrRepositorio() {
		return matAdmCrRepositorio;
	}

	public void setMatAdmCrRepositorio(String matAdmCrRepositorio) {
		this.matAdmCrRepositorio = matAdmCrRepositorio;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}	

	public long getQtdPastasRepositorio() {
		return qtdPastasRepositorio;
	}

	public void setQtdPastasRepositorio(long qtdPastasRepositorio) {
		this.qtdPastasRepositorio = qtdPastasRepositorio;
	}

	public long getQtdDocumentosRepositorio() {
		return qtdDocumentosRepositorio;
	}

	public void setQtdDocumentosRepositorio(long qtdDocumentosRepositorio) {
		this.qtdDocumentosRepositorio = qtdDocumentosRepositorio;
	}
	
}

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
@Table(name = "ARQUIVOS")
public class Arquivos extends EntidadeBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ARQUIVO", insertable = false, updatable = false)
	private long identificador;

	@Column(name = "DESC_ARQUIVO", nullable = false, length = 255)
	private String descricao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_CR_ARQUIVO", nullable = false, columnDefinition="DATETIME")
	private Date dtCrl;

	@Column(name = "MAT_ADM_CR_ARQUIVO", nullable = false, length = 50)
	private String matAdmCr;

	@Column(name = "R_OBJECT_ID", nullable = true, length = 50)
	private String objectId;
	
	@Column(name = "DT_ULT_ALT_ARQUIVO", nullable = true, length = 50)
	private String dtUltimaAtualizacao;
	
	@Column(name = "TAM_ARQUIVO", nullable = true, length = 50)
	private String tamanhoArquivo;
	
	@Column(name = "LINK_ARQUIVO", nullable = true, length = 50)
	private String linkArquivo;
	
	@Column(name = "TIPO_CONTEUDO_ARQUIVO", nullable = true, length = 50)
	private String tipoConteudo;
	
	
	public String getTipoConteudo() {
		return tipoConteudo;
	}

	public void setTipoConteudo(String tipoConteudo) {
		this.tipoConteudo = tipoConteudo;
	}

	public String getLinkArquivo() {
		return linkArquivo;
	}

	public void setLinkArquivo(String linkArquivo) {
		this.linkArquivo = linkArquivo;
	}

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

	public Arquivos() {
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

	public String getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setTamanhoArquivo(String tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}

	public String getDtUltimaAtualizacao() {
		return dtUltimaAtualizacao;
	}

	public void setDtUltimaAtualizacao(String dtUltimaAtualizacao) {
		this.dtUltimaAtualizacao = dtUltimaAtualizacao;
	}
}

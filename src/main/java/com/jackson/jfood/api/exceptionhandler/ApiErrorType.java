package com.jackson.jfood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {
	RESOURCE_NOT_FOUND ("Recurso não encontrado", "/resource-not-found"),
	ENTITY_IN_USE ("Entidade em uso", "/entity-in-use"),
	BUSINESS_ERROR ("Erro de negócio", "/business-error"),
	UNKOWN_PROPERTIES ("Propriedades desconhecidas", "/unknown-properties"),
	INVALID_PARAMETER ("Parâmetro inválido", "/invalid-parameter"),
	INVALID_DATA ("Dados Inválidos", "/invalid-data"),
	SYSTEM_ERROR ("Erro de sistema", "/system-error"),
	UNREADABLE_BODY ("Mensagem não compreensível", "/unreadable-body");
	
	private String title;
	private String uri;
	
	ApiErrorType(String title, String path) {
		this.uri = "https://jfood.com.br" + path;
		this.title = title;
	}
}

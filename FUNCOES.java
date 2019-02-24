	//VERIFICAR SE O IMPUT É VAZIO ---------------------
	private String nullToEmpty(String field) {
		return field == null ? "" : field;
	}
	// MONTAR JSON -------------------------
	private String buildRequestJSON(String token, String identifierCode, String documentType, String placeId,
			String name, String birthDate, String postalCode, String assessmentType, String queryType, String photo,
			String creditRqstDocumentType, String creditRqstDocumentImage, String creditRqstDocumentCompare)
			throws JSONException {
        String vJson = "";
		String document = "";
		if (this.hasDocument(creditRqstDocumentType)) {
			document = this.buildDocumentObjct(creditRqstDocumentType, creditRqstDocumentImage,
					creditRqstDocumentCompare);
		}
		
		if(queryType != null && queryType != "null" && !queryType.equals("")){
		
		vJson ="{  \r\n" +
				"   \"authentication\":{  \r\n" +
				"      \"token\":\"" + token + "\"\r\n" +
				"   },\r\n" +
				"   \"creditrequest\":[  \r\n" +
				"      {  \r\n" +
				"         \"identifier_code\":\"" + identifierCode + "\",\r\n" +
				"         \"document_type\":" + documentType + ",\r\n" +
				"         \"place_id\":" + placeId + ",\r\n" +
				"         \"name\":\"" + name + "\",\r\n" +
				"         \"birth_date\":\"" + birthDate + "\",\r\n" +
				"         \"postal_code\":\"" + postalCode + "\",\r\n" +
				"         \"assessment_type\":" + assessmentType + ",\r\n" +
				"         \"query_type\":" + queryType + ",\r\n" +
				"         \"photo\":\"" + photo + "\",\r\n" +
				"         \"document\":[  \r\n" +
				document +
				"         ]\r\n" +
				"      }\r\n" +
				"   ]\r\n" +
				"}";
		} else{
			vJson = "{  \r\n" +
					"   \"authentication\":{  \r\n" +
					"      \"token\":\"" + token + "\"\r\n" +
					"   },\r\n" +
					"   \"creditrequest\":[  \r\n" +
					"      {  \r\n" +
					"         \"identifier_code\":\"" + identifierCode + "\",\r\n" +
					"         \"document_type\":" + documentType + ",\r\n" +
					"         \"place_id\":" + placeId + ",\r\n" +
					"         \"name\":\"" + name + "\",\r\n" +
					"         \"birth_date\":\"" + birthDate + "\",\r\n" +
					"         \"postal_code\":\"" + postalCode + "\",\r\n" +
					"         \"assessment_type\":" + assessmentType + ",\r\n" +
					"         \"photo\":\"" + photo + "\",\r\n" +
					"         \"document\":[  \r\n" +
					document +
					"         ]\r\n" +
					"      }\r\n" +
					"   ]\r\n" +
					"}";
		}
		return vJson;
        
	}
	// MONTAR XML -------------------------------
	private String montarXmlConsultaPlaca(String pLogin, String pSenha, String pPlaca) {
		String vXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bdrf=\"http://bdrf.ws.cnseg.org.br/\">"
				   +"<soapenv:Header/>"
				   +"<soapenv:Body>"
				   +"<bdrf:BuscaPlaca>"
				   +"<Usr>"+pLogin+"</Usr>"
				   +"<Senha>"+pSenha+"</Senha>"
				   +"<Placa>"+pPlaca+"</Placa>"
				   +"</bdrf:BuscaPlaca>"
				   +"</soapenv:Body>"
				   +"</soapenv:Envelope>";
		return vXml;
	}
	
	// MONTAR URL -----------------------
	private String montarURL() throws URIException {
		String vURL = this.aHost.getProtocol() + "://" + this.aHost.getHost() + ":" + this.aHost.getPort()
				+ this.aHost.getAPath();
		return URIUtil.encodeQuery(vURL, "ISO-8859-1");
	}
	
	//METODO PARA A CLASSE GERADOR (INSPECIONA AS TAGS DO ELEMENT)
		private String inspecionarTag(Element Bloco,String Tag){
        Elements vElementos = Bloco.getElementsByTag(Tag);	
		String vRetorno = vElementos.first() == null? "" : vElementos.first().text();
		return vRetorno;
	}

//função para converter o retorno html em um xml para facilitar na extração de retorno
private String xmlReturn(String input){
	String doc;
	if(input.contains("")){
		temp = DocumentoMetodos.getConteudoMarcadores(input, "", "", false);
		String temp = "";
	}
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
	"<>"+
	"<>"+
	"<>"+input+"</>"+
	"</>"+
	"<>"+
	"<>"+doc+"</>"+
	"</>";
}

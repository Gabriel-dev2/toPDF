	@Override
	public byte[] toPDF() {
		byte[] vPDFByteArray = null;

		Parser vParser = new ParserDATABUSCAPERSONDATA();
		if (vParser.isOk(this)) {
			BuilderDATABUSCAPERSONDATA vBuilder = new BuilderDATABUSCAPERSONDATA("CORENSP", "");

			String vXml = "";
			try {
				vXml = vBuilder.gerarBuilder(this).getSiteXML();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			byte[] vHTMLByteArray = String.valueOf(vXml).getBytes();
			try {
				String vParserPath = this.getParserPath();
				if (vParserPath != null) {
					ByteArrayOutputStream vOs = new ByteArrayOutputStream();
					StreamResult vResult = new javax.xml.transform.stream.StreamResult(vOs);

					String[] vCampos = vXml.split("<C nome=");
					for (int i = 1; i < vCampos.length; i++) {
						String vCampo = vCampos[i];

						String vValorCampo = DocumentMetodos.getConteudoMarcadores(vCampo, ">", "<", false).trim();
						vXml = vXml.replace(vValorCampo, StringEscapeUtils.escapeXml(vValorCampo));
					}

					vXml = vXml.replace("null", "");
					ByteArrayInputStream vByteArrayXml = new ByteArrayInputStream(vXml.getBytes("ISO-8859-1"));
					TransformerFactory tFactory = TransformerFactory.newInstance();
					Transformer transformer = tFactory.newTransformer(new StreamSource(this.getParserPath()));
					transformer.transform(new StreamSource(vByteArrayXml), vResult);
					String vStringFormatoUTF8 = new String(vOs.toByteArray(), "ISO-8859-1");
					vHTMLByteArray = vStringFormatoUTF8.getBytes();
				}

				vPDFByteArray = NHtml2Pdf.convert(vHTMLByteArray);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (vParser.isNotFound(this)) {
			vPDFByteArray = NHtml2Pdf.convert("NAO ENCONTRADO".getBytes());
		} else {
			List<ValidatorDocument> vListaValidadores = new ArrayList<ValidatorDocument>();
			if (vParser.isSpecial(this) != null) {
				 vListaValidadores = Arrays.asList((ValidatorDocument[]) vParser.getParserSpecials());
			} else {
				vListaValidadores.addAll(Arrays.asList((ValidatorDocument[]) vParser.getParserFails()));
			}

			for (ValidatorDocument vValidator : vListaValidadores) {
				if (vValidator.validateDocument(this)) {
					vPDFByteArray = NHtml2Pdf.convert(vValidator.message().getBytes());
					break;
				}
			}
		}

		return vPDFByteArray;
	}
## Ocr Api with Tesseract-ocr
	
### How to use
```
	HttpPost httppost = new HttpPost("http://localhost:8010/ocr-api/");

    FileBody bin = new FileBody(new File("E:\\OCR\\2233373112.jpg"));
    StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

    HttpEntity reqEntity = MultipartEntityBuilder.create()
            .addPart("file", bin)
            .addPart("comment", comment)
            .build();

    httppost.setEntity(reqEntity);

    CloseableHttpResponse response = httpclient.execute(httppost);
    try {
        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
        	List<String> readLines = IOUtils.readLines(resEntity.getContent(), OcrApiUtils.UTF8);
            System.out.println("Response content length: " + resEntity.getContentLength());
            System.out.println(ListUtils.toString(readLines));
        }
        EntityUtils.consume(resEntity);
    } finally {
        response.close();
    }
```
	
### Api
[Api](http://101.132.171.147:81/ocr-api/swagger-ui.html)

### demo
[Demo](http://101.132.171.147:81/ocr-api/demo)

![Demo](src/main/resources/static/demo.png)
<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://services.showcase.acceptancetest.tapack.io/">
    <soapenv:Header/>
    <soapenv:Body>
        <ser:getWeatherFor>
            <arg0>${city}</arg0>
        </ser:getWeatherFor>
    </soapenv:Body>
</soapenv:Envelope>
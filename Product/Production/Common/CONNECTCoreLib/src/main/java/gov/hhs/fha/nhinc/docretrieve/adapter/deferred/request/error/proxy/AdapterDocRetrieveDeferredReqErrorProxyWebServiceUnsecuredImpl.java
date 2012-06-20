/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services. 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *     * Redistributions of source code must retain the above 
 *       copyright notice, this list of conditions and the following disclaimer. 
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the documentation 
 *       and/or other materials provided with the distribution. 
 *     * Neither the name of the United States Government nor the 
 *       names of its contributors may be used to endorse or promote products 
 *       derived from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package gov.hhs.fha.nhinc.docretrieve.adapter.deferred.request.error.proxy;

import gov.hhs.fha.nhinc.adapterdocretrievedeferredrequesterror.AdapterDocRetrieveDeferredRequestErrorPortType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.AdapterDocumentRetrieveDeferredRequestErrorType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants.ADAPTER_API_LEVEL;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper;
import gov.hhs.healthit.nhin.DocRetrieveAcknowledgementType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;

/**
 * Created by User: ralph Date: Jul 26, 2010 Time: 2:37:22 PM
 */
public class AdapterDocRetrieveDeferredReqErrorProxyWebServiceUnsecuredImpl implements
        AdapterDocRetrieveDeferredReqErrorProxy {

    private Log log = null;
    private static Service cachedService = null;
    private static final String NAMESPACE_URI = "urn:gov:hhs:fha:nhinc:adapterdocretrievedeferredrequesterror";
    private static final String SERVICE_LOCAL_PART = "AdapterDocRetrieveDeferredRequestErrorService";
    private static final String PORT_LOCAL_PART = "AdapterDocRetrieveRequestErrorPortSoap";
    private static final String WSDL_FILE = "AdapterDocRetrieveDeferredReqError.wsdl";
    private static final String WS_ADDRESSING_ACTION = "urn:gov:hhs:fha:nhinc:adapterdocretrievedeferredrequest:CrossGatewayRetrieveRequestErrorMessage";
    private WebServiceProxyHelper oProxyHelper = null;

    public AdapterDocRetrieveDeferredReqErrorProxyWebServiceUnsecuredImpl() {
        log = createLogger();
        oProxyHelper = createWebServiceProxyHelper();
    }

    protected Log createLogger() {
        return LogFactory.getLog(getClass());
    }

    protected WebServiceProxyHelper createWebServiceProxyHelper() {
        return new WebServiceProxyHelper();
    }

    /**
     * This method retrieves and initializes the port.
     * 
     * @param url The URL for the web service.
     * @return The port object for the web service.
     */
    protected AdapterDocRetrieveDeferredRequestErrorPortType getPort(String url, String wsAddressingAction,
            AssertionType assertion) {
        AdapterDocRetrieveDeferredRequestErrorPortType port = null;
        Service service = getService();
        if (service != null) {
            log.debug("Obtained service - creating port.");

            port = service.getPort(new QName(NAMESPACE_URI, PORT_LOCAL_PART),
                    AdapterDocRetrieveDeferredRequestErrorPortType.class);
            oProxyHelper
                    .initializeUnsecurePort((javax.xml.ws.BindingProvider) port, url, wsAddressingAction, assertion);
        } else {
            log.error("Unable to obtain serivce - no port created.");
        }
        return port;
    }

    /**
     * Retrieve the service class for this web service.
     * 
     * @return The service class for this web service.
     */
    protected Service getService() {
        if (cachedService == null) {
            try {
                cachedService = oProxyHelper.createService(WSDL_FILE, NAMESPACE_URI, SERVICE_LOCAL_PART);
            } catch (Throwable t) {
                log.error("Error creating service: " + t.getMessage(), t);
            }
        }
        return cachedService;
    }

    public DocRetrieveAcknowledgementType sendToAdapter(RetrieveDocumentSetRequestType body, AssertionType assertion,
            String errMsg) {
        log.debug("Begin sendToAdapter");
        DocRetrieveAcknowledgementType response = null;

        try {
            String url = oProxyHelper
                    .getAdapterEndPointFromConnectionManager(NhincConstants.ADAPTER_DOC_RETRIEVE_DEFERRED_REQUEST_ERROR_SERVICE_NAME);
            if (NullChecker.isNotNullish(url)) {
                AdapterDocRetrieveDeferredRequestErrorPortType port = getPort(url, WS_ADDRESSING_ACTION, assertion);

                if (body == null) {
                    log.error("Message was null");
                } else if (assertion == null) {
                    log.error("AssertionType was null");
                } else if (NullChecker.isNullish(errMsg)) {
                    log.error("errMsg was null");
                } else if (port == null) {
                    log.error("port was null");
                } else {
                    AdapterDocumentRetrieveDeferredRequestErrorType request = new AdapterDocumentRetrieveDeferredRequestErrorType();
                    request.setRetrieveDocumentSetRequest(body);
                    request.setAssertion(assertion);
                    request.setErrorMsg(errMsg);

                    response = (DocRetrieveAcknowledgementType) oProxyHelper.invokePort(port,
                            AdapterDocRetrieveDeferredRequestErrorPortType.class, "crossGatewayRetrieveRequestError",
                            request);
                }
            } else {
                log.error("Failed to call the web service ("
                        + NhincConstants.ADAPTER_DOC_RETRIEVE_DEFERRED_REQUEST_ERROR_SERVICE_NAME
                        + ").  The URL is null.");
            }
        } catch (Exception ex) {
            log.error("Error calling crossGatewayRetrieveRequestError: " + ex.getMessage(), ex);
            response = new DocRetrieveAcknowledgementType();
            RegistryResponseType regResp = new RegistryResponseType();
            regResp.setStatus(NhincConstants.DOC_RETRIEVE_DEFERRED_REQ_ACK_STATUS_MSG);
            response.setMessage(regResp);
        }

        log.debug("End sendToAdapter");
        return response;
    }
}
/**
 * Copyright (c) 2009-2014, United States Government, as represented by the Secretary of Health and Human Services. All
 * rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution. Neither the name of the United States Government nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package gov.hhs.fha.nhinc.admingui.proxy;

import gov.hhs.fha.nhinc.admingui.proxy.service.DirectConfigUnsecuredServicePortDescriptor;
import gov.hhs.fha.nhinc.messaging.client.CONNECTCXFClientFactory;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClient;
import gov.hhs.fha.nhinc.messaging.service.port.ServicePortDescriptor;
import gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper;

import java.util.List;
import org.nhind.config.AddAnchor;
import org.nhind.config.AddCertificates;
import org.nhind.config.AddDomain;
import org.nhind.config.AddTrustBundle;
import org.nhind.config.Anchor;
import org.nhind.config.AssociateTrustBundleToDomain;
import org.nhind.config.Certificate;
import org.nhind.config.ConfigurationService;
import org.nhind.config.DeleteTrustBundles;
import org.nhind.config.DisassociateTrustBundleFromDomains;
import org.nhind.config.Domain;
import org.nhind.config.GetAnchorsForOwner;
import org.nhind.config.GetTrustBundleByName;
import org.nhind.config.GetTrustBundles;
import org.nhind.config.GetTrustBundlesByDomain;
import org.nhind.config.ListCertificates;
import org.nhind.config.RemoveAnchors;
import org.nhind.config.RemoveCertificates;
import org.nhind.config.Setting;
import org.nhind.config.TrustBundle;
import org.nhind.config.TrustBundleDomainReltn;
import org.nhind.config.UpdateDomain;
import org.nhind.config.UpdateDomainResponse;
import org.nhind.config.UpdateTrustBundleAttributes;
import org.springframework.stereotype.Service;

/**
 *
 * @author jasonasmith
 */
@SuppressWarnings("unchecked")
@Service
public class DirectConfigProxyWebServiceUnsecuredImpl implements DirectConfigProxy {

    private final WebServiceProxyHelper oProxyHelper = new WebServiceProxyHelper();

    private final Class<ConfigurationService> directConfigClazz = ConfigurationService.class;

    @Override
    public Domain getDomain(Long id) throws Exception {
        return (Domain) getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_GET_DOMAIN, id);
    }

    @Override
    public void addDomain(AddDomain domain) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_ADD_DOMAIN, domain);
    }

    @Override
    public List<Domain> listDomains() throws Exception {
        return (List<Domain>) getClient().invokePort(directConfigClazz,
                DirectConfigConstants.DIRECT_CONFIG_LIST_DOMAINS, null, 0);
    }

    @Override
    public UpdateDomainResponse updateDomain(UpdateDomain updateDomain) throws Exception {
        return (UpdateDomainResponse) getClient().invokePort(directConfigClazz,
                DirectConfigConstants.DIRECT_CONFIG_UPDATE_DOMAIN, updateDomain);
    }

    @Override
    public void deleteDomain(String name) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_DELETE_DOMAIN, name);
    }

    @Override
    public void addAnchor(AddAnchor addAnchor) throws Exception {
        getClient()
            .invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_ADD_ANCHOR, addAnchor.getAnchor());
    }

    @Override
    public void removeAnchors(RemoveAnchors removeAnchors) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_DELETE_ANCHOR,
            removeAnchors.getAnchorId());
    }

    @Override
    public List<Anchor> getAnchorsForOwner(GetAnchorsForOwner getAnchorsForOwner) throws Exception {
        return (List<Anchor>) getClient().invokePort(directConfigClazz,
            DirectConfigConstants.DIRECT_CONFIG_GET_ANCHORS_FOR_OWNER, getAnchorsForOwner.getOwner(),
            getAnchorsForOwner.getOptions());
    }

    @Override
    public void addCertificates(AddCertificates certificate) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_ADD_CERT, certificate.getCerts());

    }

    @Override
    public void removeCertificate(RemoveCertificates certificate) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_DELETE_CERT,
                certificate.getCertificateIds());

    }

    @Override
    public List<Certificate> listCertificates(ListCertificates listCert) throws Exception {
        return (List<Certificate>) getClient().invokePort(directConfigClazz,
                DirectConfigConstants.DIRECT_CONFIG_LIST_CERTS, listCert.getLastCertificateId(),
                listCert.getMaxResutls(), listCert.getOptions());
    }

    @Override
    public void addSetting(String name, String value) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_ADD_SETTING, name, value);
    }

    @Override
    public List<Setting> getSetting() throws Exception {
        return (List<Setting>) getClient().invokePort(directConfigClazz,
            DirectConfigConstants.DIRECT_CONFIG_LIST_SETTINGS);
    }

    @Override
    public void deleteSetting(List<String> deleteNames) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_DELETE_SETTING, deleteNames);
    }

    @Override
    public void addTrustBundle(TrustBundle tb) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_ADD_TRUST_BUNDLE, tb);
    }

    @Override
    public List<TrustBundle> getTrustBundles(boolean fetchAnchors) throws Exception {
        return (List<TrustBundle>) getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_GET_TRUST_BUNDLES, fetchAnchors);
    }

    @Override
    public TrustBundle getTrustBundleByName(String bundleName) throws Exception {
        return (TrustBundle) getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_GET_TRUST_BUNDLE_BY_NAME, bundleName);
    }

    @Override
    public List<TrustBundleDomainReltn> getTrustBundlesByDomain(long domainId, boolean fetchAnchors) throws Exception {
        return (List<TrustBundleDomainReltn>) getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_GET_TRUST_BUNDLES_BY_DOMAIN, domainId, fetchAnchors);
    }

    @Override
    public void associateTrustBundleToDomain(long domainId, long trustBundleId, boolean incoming, boolean outgoing) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_ASSOCIATE_TRUST_BUNDLE_TO_DOMAIN, domainId, trustBundleId, incoming, outgoing);
    }

    @Override
    public void updateTrustBundleAttributes(long trustBundleId, String trustBundleName, String trustBundleURL,
        Certificate signingCert, int trustBundleRefreshInterval) throws Exception {

        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_UPDATE_TRUST_BUNDLE_ATTRIBUTES,
            trustBundleId, trustBundleName, trustBundleURL, signingCert, trustBundleRefreshInterval);
    }

    @Override
    public void refreshTrustBundle(int id) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_REFRESH_TRUST_BUNDLE, id);
    }

    @Override
    public void disassociateTrustBundleFromDomain(long domainId, long trustBundleId) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_DISASSOCIATE_TRUST_BUNDLE_FROM_DOMAIN, domainId, trustBundleId);
    }

    @Override
    public void disassociateTrustBundleFromDomains(long trustBundleId) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_DISASSOCIATE_TRUST_BUNDLE_FROM_DOMAINS, trustBundleId);
    }

    @Override
    public void disassociateTrustBundlesFromDomain(long domainId) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_DISASSOCIATE_TRUST_BUNDLES_FROM_DOMAIN, domainId);
    }

    @Override
    public void deleteTrustBundles(List<Long> ids) throws Exception {
        getClient().invokePort(directConfigClazz, DirectConfigConstants.DIRECT_CONFIG_DELETE_TRUST_BUNDLE, ids);
    }

    private CONNECTClient<ConfigurationService> getClient() throws Exception {

        String url = oProxyHelper
                .getAdapterEndPointFromConnectionManager(DirectConfigConstants.DIRECT_CONFIG_SERVICE_NAME);

        ServicePortDescriptor<ConfigurationService> portDescriptor = new DirectConfigUnsecuredServicePortDescriptor();

        CONNECTClient<ConfigurationService> client = CONNECTCXFClientFactory.getInstance().getCONNECTClientUnsecured(
                portDescriptor, url, null);

        return client;
    }
}

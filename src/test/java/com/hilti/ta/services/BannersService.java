package com.hilti.ta.services;

import com.hilti.ta.utils.BannerData;
import com.hilti.ta.utils.Country;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Banners service responsible for retrieving legal, marketing banners appearing for given country.
 */
public class BannersService {

	private static final String BANNERS_URL_PATTERN = "http://hilti-prod.adobecqms.net/bin/hilti/servlets/getBannersForLocale.json?locale=%s";

	private ObjectMapper jacksonObjectMapper = new ObjectMapper();

	/**
	 * Retrieves banner ids for a given country.
	 * 
	 * @param country
	 *            {@link Country}
	 * @return list of banner ids
	 */
	public List<String> getBannerIdsForCountry(final Country country) {
		final List<String> bannerIds = new ArrayList<>();
		try {
			final String url = String.format(BANNERS_URL_PATTERN, country.getDefaultLocale());
			final Connection connect = Jsoup.connect(url);

			final String response = connect.ignoreContentType(true).execute().body();

			if (StringUtils.isNotBlank(response)) {
				final List<BannerData> banners = jacksonObjectMapper.readValue(response, new TypeReference<ArrayList<BannerData>>() {
				});
				banners.forEach(banner -> bannerIds.add(banner.getId()));
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return bannerIds;
	}
}

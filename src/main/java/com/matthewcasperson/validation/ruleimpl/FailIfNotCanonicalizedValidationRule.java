/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Matthew Casperson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.matthewcasperson.validation.ruleimpl;

import com.matthewcasperson.validation.exception.ValidationFailedException;
import com.matthewcasperson.validation.rule.ParameterValidationRuleTemplate;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.reference.DefaultEncoder;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A validation rule that will cause the request to fail if the parameter has been
 * encoded in some way.
 * @author mcasperson
 */
public class FailIfNotCanonicalizedValidationRule extends ParameterValidationRuleTemplate {

	private static final String ALLOW_BACK_SLASH = "allowBackSlash";
	private boolean allowBackSlash = false;

	@Override
	public void configure(final Map<String, String> settings) {
		if (settings.containsKey(ALLOW_BACK_SLASH)) {
			allowBackSlash = Boolean.parseBoolean(settings.get(ALLOW_BACK_SLASH));
		}
	}

	@Override
	public String[] fixParams(final String name, final String url, final String[] params) throws ValidationFailedException {
		checkNotNull(name);
		checkArgument(!name.trim().isEmpty());
		checkNotNull(url);
		checkArgument(!url.trim().isEmpty());
		checkNotNull(params);
		checkArgument(params.length != 0, "PVF-BUG-0003: params should always have at least one value");

		for (int paramIndex = 0, paramLength = params.length; paramIndex < paramLength; ++paramIndex) {
			String param = params[paramIndex];

			if (allowBackSlash) {
				param = param.replaceAll("\\\\", "");
			}

			if (param != null) {
				final Encoder encoder = DefaultEncoder.getInstance();
				final String canonicalized = encoder.canonicalize(param, false);

				if (!canonicalized.equals(param)) {
					throw new ValidationFailedException("PVF-SECURITY-0002: Param was found to already be encoded.\nNAME: " + name + "\nVALUE: " + param + "\nURL: " + url);
				}
			}
		}

		return params;
	}

}

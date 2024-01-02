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
package com.matthewcasperson.validation.utilsimpl;

import com.matthewcasperson.validation.utils.RequestParameterUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public class RequestParameterUtilsImpl implements RequestParameterUtils {

	@Override
	public String getParam(final ServletRequest request, final String name) {
		checkNotNull(request);
		checkNotNull(name);
		checkArgument(!name.trim().isEmpty());

		if (request instanceof HttpServletRequest) {
			final HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			return getParam(httpServletRequest, name);
		}

		return null;
	}

	@Override
	public String getParam(final HttpServletRequest request, final String name) {
		checkNotNull(request);
		checkNotNull(name);
		checkArgument(!name.trim().isEmpty());

		final String parameterValue = request.getParameter(name);
		return parameterValue;
	}

	@Override
	public String[] getParams(final ServletRequest request, final String name) {
		checkNotNull(request);
		checkNotNull(name);
		checkArgument(!name.trim().isEmpty());

		if (request instanceof HttpServletRequest) {
			final HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			return getParams(httpServletRequest, name);
		}

		return null;
	}

	@Override
	public String[] getParams(final HttpServletRequest request, final String name) {
		checkNotNull(request);
		checkNotNull(name);
		checkArgument(!name.trim().isEmpty());

		final String[] parameterValue = request.getParameterValues(name);
		return parameterValue;
	}

}

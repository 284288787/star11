/**create by liuhua at 2017年5月26日 上午9:41:06**/
package com.star.truffle.core.security;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class StarAccessDecisionManager extends AbstractAccessDecisionManager {

	public StarAccessDecisionManager(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
		super(decisionVoters);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
		for (AccessDecisionVoter voter : getDecisionVoters()) {
			int result = voter.vote(authentication, object, configAttributes);

			if (logger.isDebugEnabled()) {
				logger.debug("Voter: " + voter + ", returned: " + result);
			}

			switch (result) {
			case AccessDecisionVoter.ACCESS_GRANTED:
				return;

			case AccessDecisionVoter.ACCESS_DENIED:
				break;
			default:
				break;
			}
		}

		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
//			System.out.println("authentication size" + authentication.getAuthorities().size());
			// ga 为用户所被赋予的权限。 needRole 为访问相应的资源应该具有的权限。
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.trim().equals(ga.getAuthority().trim())) {
					return;
				}
			}
		}
		String msg = messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied");
		AccessDeniedException accessDeniedException = new AccessDeniedException(msg);
		throw accessDeniedException;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}

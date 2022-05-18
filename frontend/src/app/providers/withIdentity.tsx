import { useEffect, useState } from 'react';
import type { FC } from 'react';

import { IdentityContext } from 'entities/identity';

export const withIdentity = (Component: FC) => {
  const IdentityProvider = () => {
    const [accessToken, setAccessToken] = useState<string | undefined>(
      undefined,
    );

    useEffect(() => {
      const token = localStorage.getItem('access-token');

      if (token) {
        setAccessToken(token);
      }
    }, []);

    useEffect(() => {
      if (accessToken === undefined) {
        localStorage.removeItem('access-token');
        return;
      }

      localStorage.setItem('access-token', accessToken);
    }, [accessToken]);

    return (
      <IdentityContext.Provider value={[accessToken, setAccessToken]}>
        <Component />
      </IdentityContext.Provider>
    );
  };

  IdentityProvider.displayName = 'IdentityProvider';

  return IdentityProvider;
};

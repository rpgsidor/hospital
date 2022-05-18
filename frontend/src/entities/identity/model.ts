import type { Dispatch } from 'react';
import { createContext, useContext } from 'react';

type ContextValue = [string | undefined, Dispatch<string | undefined>] | null;

export const IdentityContext = createContext<ContextValue>(null);
IdentityContext.displayName = 'IdentityContext';

export const useIdentityContext = () => {
  const identityContext = useContext(IdentityContext);

  if (identityContext === null) {
    throw new Error('Identity context can not be null');
  }

  return identityContext;
};

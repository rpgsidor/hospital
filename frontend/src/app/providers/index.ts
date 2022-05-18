import compose from 'compose-function';

import { withIdentity } from './withIdentity';
import { withRouter } from './withRouter';
import { withStrictMode } from './withStrictMode';
import { withTheme } from './withTheme';

export const withProviders = compose(
  withStrictMode,
  withRouter,
  withTheme,
  withIdentity,
);

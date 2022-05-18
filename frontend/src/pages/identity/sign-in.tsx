import { Box } from '@mui/material';

import { SignInForm } from 'features/sign-in';

export const SignInPage = () => (
  <>
    <h1>Sign in</h1>

    <Box sx={{ width: '600px', m: 'auto' }}>
      <SignInForm />
    </Box>
  </>
);

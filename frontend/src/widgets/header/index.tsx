import {
  AppBar,
  Box,
  Button,
  Container,
  Toolbar,
  Typography,
} from '@mui/material';
import { NavLink } from 'react-router-dom';

import { useIdentityContext } from 'entities/identity';

export const Header = () => {
  const [accessToken, setAccessToken] = useIdentityContext();

  const signedIn = accessToken !== undefined;

  const onSignOut = () => {
    setAccessToken(undefined);
  };

  return (
    <AppBar position={'static'}>
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <Typography
            variant="h6"
            component={NavLink}
            to={'/'}
            noWrap
            sx={{
              color: 'inherit',
              mr: 3,
              textDecoration: 'none',
            }}
          >
            Hospital
          </Typography>

          <Box sx={{ flexGrow: 1, display: 'flex' }}>
            <Button
              component={NavLink}
              to={'/wards'}
              sx={{
                textDecoration: 'none',
                my: 2,
                color: 'white',
              }}
            >
              Wards
            </Button>

            <Button
              component={NavLink}
              to={'/diagnoses'}
              sx={{
                textDecoration: 'none',
                my: 2,
                color: 'white',
              }}
            >
              Diagnoses
            </Button>

            {!signedIn ? (
              <Button
                component={NavLink}
                to={'/identity/sign-in'}
                sx={{
                  textDecoration: 'none',
                  my: 2,
                  ml: 'auto',
                  color: 'white',
                }}
              >
                Sign in
              </Button>
            ) : (
              <Button
                onClick={onSignOut}
                sx={{
                  textDecoration: 'none',
                  my: 2,
                  ml: 'auto',
                  color: 'white',
                }}
              >
                Sign out
              </Button>
            )}
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
};

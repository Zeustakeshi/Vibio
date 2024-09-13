/* prettier-ignore-start */

/* eslint-disable */

// @ts-nocheck

// noinspection JSUnusedGlobalSymbols

// This file is auto-generated by TanStack Router

import { createFileRoute } from '@tanstack/react-router'

// Import Routes

import { Route as rootRoute } from './routes/__root'
import { Route as appIndexImport } from './routes/(app)/index'
import { Route as AuthAuthImport } from './routes/auth/_auth'
import { Route as AuthOtpOtpImport } from './routes/auth/otp/_otp'
import { Route as AuthAuthRegisterImport } from './routes/auth/_auth.register'
import { Route as AuthAuthLoginImport } from './routes/auth/_auth.login'
import { Route as AuthOtpOtpNewAccountImport } from './routes/auth/otp/_otp.new-account'
import { Route as AuthOtpOtpMfaImport } from './routes/auth/otp/_otp.mfa'

// Create Virtual Routes

const AuthImport = createFileRoute('/auth')()
const AuthOtpImport = createFileRoute('/auth/otp')()

// Create/Update Routes

const AuthRoute = AuthImport.update({
  path: '/auth',
  getParentRoute: () => rootRoute,
} as any)

const AuthOtpRoute = AuthOtpImport.update({
  path: '/otp',
  getParentRoute: () => AuthRoute,
} as any)

const appIndexRoute = appIndexImport.update({
  path: '/',
  getParentRoute: () => rootRoute,
} as any)

const AuthAuthRoute = AuthAuthImport.update({
  id: '/_auth',
  getParentRoute: () => AuthRoute,
} as any)

const AuthOtpOtpRoute = AuthOtpOtpImport.update({
  id: '/_otp',
  getParentRoute: () => AuthOtpRoute,
} as any)

const AuthAuthRegisterRoute = AuthAuthRegisterImport.update({
  path: '/register',
  getParentRoute: () => AuthAuthRoute,
} as any)

const AuthAuthLoginRoute = AuthAuthLoginImport.update({
  path: '/login',
  getParentRoute: () => AuthAuthRoute,
} as any)

const AuthOtpOtpNewAccountRoute = AuthOtpOtpNewAccountImport.update({
  path: '/new-account',
  getParentRoute: () => AuthOtpOtpRoute,
} as any)

const AuthOtpOtpMfaRoute = AuthOtpOtpMfaImport.update({
  path: '/mfa',
  getParentRoute: () => AuthOtpOtpRoute,
} as any)

// Populate the FileRoutesByPath interface

declare module '@tanstack/react-router' {
  interface FileRoutesByPath {
    '/auth': {
      id: '/auth'
      path: '/auth'
      fullPath: '/auth'
      preLoaderRoute: typeof AuthImport
      parentRoute: typeof rootRoute
    }
    '/auth/_auth': {
      id: '/auth/_auth'
      path: '/auth'
      fullPath: '/auth'
      preLoaderRoute: typeof AuthAuthImport
      parentRoute: typeof AuthRoute
    }
    '/(app)/': {
      id: '/'
      path: '/'
      fullPath: '/'
      preLoaderRoute: typeof appIndexImport
      parentRoute: typeof rootRoute
    }
    '/auth/_auth/login': {
      id: '/auth/_auth/login'
      path: '/login'
      fullPath: '/auth/login'
      preLoaderRoute: typeof AuthAuthLoginImport
      parentRoute: typeof AuthAuthImport
    }
    '/auth/_auth/register': {
      id: '/auth/_auth/register'
      path: '/register'
      fullPath: '/auth/register'
      preLoaderRoute: typeof AuthAuthRegisterImport
      parentRoute: typeof AuthAuthImport
    }
    '/auth/otp': {
      id: '/auth/otp'
      path: '/otp'
      fullPath: '/auth/otp'
      preLoaderRoute: typeof AuthOtpImport
      parentRoute: typeof AuthImport
    }
    '/auth/otp/_otp': {
      id: '/auth/otp/_otp'
      path: '/otp'
      fullPath: '/auth/otp'
      preLoaderRoute: typeof AuthOtpOtpImport
      parentRoute: typeof AuthOtpRoute
    }
    '/auth/otp/_otp/mfa': {
      id: '/auth/otp/_otp/mfa'
      path: '/mfa'
      fullPath: '/auth/otp/mfa'
      preLoaderRoute: typeof AuthOtpOtpMfaImport
      parentRoute: typeof AuthOtpOtpImport
    }
    '/auth/otp/_otp/new-account': {
      id: '/auth/otp/_otp/new-account'
      path: '/new-account'
      fullPath: '/auth/otp/new-account'
      preLoaderRoute: typeof AuthOtpOtpNewAccountImport
      parentRoute: typeof AuthOtpOtpImport
    }
  }
}

// Create and export the route tree

interface AuthAuthRouteChildren {
  AuthAuthLoginRoute: typeof AuthAuthLoginRoute
  AuthAuthRegisterRoute: typeof AuthAuthRegisterRoute
}

const AuthAuthRouteChildren: AuthAuthRouteChildren = {
  AuthAuthLoginRoute: AuthAuthLoginRoute,
  AuthAuthRegisterRoute: AuthAuthRegisterRoute,
}

const AuthAuthRouteWithChildren = AuthAuthRoute._addFileChildren(
  AuthAuthRouteChildren,
)

interface AuthOtpOtpRouteChildren {
  AuthOtpOtpMfaRoute: typeof AuthOtpOtpMfaRoute
  AuthOtpOtpNewAccountRoute: typeof AuthOtpOtpNewAccountRoute
}

const AuthOtpOtpRouteChildren: AuthOtpOtpRouteChildren = {
  AuthOtpOtpMfaRoute: AuthOtpOtpMfaRoute,
  AuthOtpOtpNewAccountRoute: AuthOtpOtpNewAccountRoute,
}

const AuthOtpOtpRouteWithChildren = AuthOtpOtpRoute._addFileChildren(
  AuthOtpOtpRouteChildren,
)

interface AuthOtpRouteChildren {
  AuthOtpOtpRoute: typeof AuthOtpOtpRouteWithChildren
}

const AuthOtpRouteChildren: AuthOtpRouteChildren = {
  AuthOtpOtpRoute: AuthOtpOtpRouteWithChildren,
}

const AuthOtpRouteWithChildren =
  AuthOtpRoute._addFileChildren(AuthOtpRouteChildren)

interface AuthRouteChildren {
  AuthAuthRoute: typeof AuthAuthRouteWithChildren
  AuthOtpRoute: typeof AuthOtpRouteWithChildren
}

const AuthRouteChildren: AuthRouteChildren = {
  AuthAuthRoute: AuthAuthRouteWithChildren,
  AuthOtpRoute: AuthOtpRouteWithChildren,
}

const AuthRouteWithChildren = AuthRoute._addFileChildren(AuthRouteChildren)

export interface FileRoutesByFullPath {
  '/auth': typeof AuthAuthRouteWithChildren
  '/': typeof appIndexRoute
  '/auth/login': typeof AuthAuthLoginRoute
  '/auth/register': typeof AuthAuthRegisterRoute
  '/auth/otp': typeof AuthOtpOtpRouteWithChildren
  '/auth/otp/mfa': typeof AuthOtpOtpMfaRoute
  '/auth/otp/new-account': typeof AuthOtpOtpNewAccountRoute
}

export interface FileRoutesByTo {
  '/auth': typeof AuthAuthRouteWithChildren
  '/': typeof appIndexRoute
  '/auth/login': typeof AuthAuthLoginRoute
  '/auth/register': typeof AuthAuthRegisterRoute
  '/auth/otp': typeof AuthOtpOtpRouteWithChildren
  '/auth/otp/mfa': typeof AuthOtpOtpMfaRoute
  '/auth/otp/new-account': typeof AuthOtpOtpNewAccountRoute
}

export interface FileRoutesById {
  __root__: typeof rootRoute
  '/auth': typeof AuthRouteWithChildren
  '/auth/_auth': typeof AuthAuthRouteWithChildren
  '/': typeof appIndexRoute
  '/auth/_auth/login': typeof AuthAuthLoginRoute
  '/auth/_auth/register': typeof AuthAuthRegisterRoute
  '/auth/otp': typeof AuthOtpRouteWithChildren
  '/auth/otp/_otp': typeof AuthOtpOtpRouteWithChildren
  '/auth/otp/_otp/mfa': typeof AuthOtpOtpMfaRoute
  '/auth/otp/_otp/new-account': typeof AuthOtpOtpNewAccountRoute
}

export interface FileRouteTypes {
  fileRoutesByFullPath: FileRoutesByFullPath
  fullPaths:
    | '/auth'
    | '/'
    | '/auth/login'
    | '/auth/register'
    | '/auth/otp'
    | '/auth/otp/mfa'
    | '/auth/otp/new-account'
  fileRoutesByTo: FileRoutesByTo
  to:
    | '/auth'
    | '/'
    | '/auth/login'
    | '/auth/register'
    | '/auth/otp'
    | '/auth/otp/mfa'
    | '/auth/otp/new-account'
  id:
    | '__root__'
    | '/auth'
    | '/auth/_auth'
    | '/'
    | '/auth/_auth/login'
    | '/auth/_auth/register'
    | '/auth/otp'
    | '/auth/otp/_otp'
    | '/auth/otp/_otp/mfa'
    | '/auth/otp/_otp/new-account'
  fileRoutesById: FileRoutesById
}

export interface RootRouteChildren {
  AuthRoute: typeof AuthRouteWithChildren
  appIndexRoute: typeof appIndexRoute
}

const rootRouteChildren: RootRouteChildren = {
  AuthRoute: AuthRouteWithChildren,
  appIndexRoute: appIndexRoute,
}

export const routeTree = rootRoute
  ._addFileChildren(rootRouteChildren)
  ._addFileTypes<FileRouteTypes>()

/* prettier-ignore-end */

/* ROUTE_MANIFEST_START
{
  "routes": {
    "__root__": {
      "filePath": "__root.tsx",
      "children": [
        "/auth",
        "/"
      ]
    },
    "/auth": {
      "filePath": "auth",
      "children": [
        "/auth/_auth",
        "/auth/otp"
      ]
    },
    "/auth/_auth": {
      "filePath": "auth/_auth.tsx",
      "parent": "/auth",
      "children": [
        "/auth/_auth/login",
        "/auth/_auth/register"
      ]
    },
    "/": {
      "filePath": "(app)/index.tsx"
    },
    "/auth/_auth/login": {
      "filePath": "auth/_auth.login.tsx",
      "parent": "/auth/_auth"
    },
    "/auth/_auth/register": {
      "filePath": "auth/_auth.register.tsx",
      "parent": "/auth/_auth"
    },
    "/auth/otp": {
      "filePath": "auth/otp",
      "parent": "/auth",
      "children": [
        "/auth/otp/_otp"
      ]
    },
    "/auth/otp/_otp": {
      "filePath": "auth/otp/_otp.tsx",
      "parent": "/auth/otp",
      "children": [
        "/auth/otp/_otp/mfa",
        "/auth/otp/_otp/new-account"
      ]
    },
    "/auth/otp/_otp/mfa": {
      "filePath": "auth/otp/_otp.mfa.tsx",
      "parent": "/auth/otp/_otp"
    },
    "/auth/otp/_otp/new-account": {
      "filePath": "auth/otp/_otp.new-account.tsx",
      "parent": "/auth/otp/_otp"
    }
  }
}
ROUTE_MANIFEST_END */
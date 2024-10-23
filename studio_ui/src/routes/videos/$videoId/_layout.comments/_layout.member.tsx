import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute(
  '/videos/$videoId/_layout/comments/_layout/member',
)({
  component: () => <div>Hello /videos/$videoId/_layout/comments/member!</div>,
})

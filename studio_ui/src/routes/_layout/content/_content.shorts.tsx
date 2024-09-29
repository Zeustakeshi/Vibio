import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/_layout/content/_content/shorts')({
  component: Shorts,
})

function Shorts() {
  return <div>hello</div>
}

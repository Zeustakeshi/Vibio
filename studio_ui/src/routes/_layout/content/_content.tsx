import ContentNavbar from '@/modules/content/navbar/ContentNavbar'
import { createFileRoute, Outlet } from '@tanstack/react-router'

export const Route = createFileRoute('/_layout/content/_content')({
  component: LayoutContent,
})

function LayoutContent() {
  return (
    <div>
      <h1 className="text-xl font-semibold my-3">Nội dung kênh</h1>
      <ContentNavbar></ContentNavbar>
      <div className="my-3">
        <Outlet></Outlet>
      </div>
    </div>
  )
}

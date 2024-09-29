import ChannelAnalytics from '@/modules/dashboard/ChannelAnalytics'
import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/_layout/')({
  component: Home,
})

function Home() {
  return (
    <div>
      <h1 className="text-xl font-semibold my-3">Tổng quan kênh</h1>
      <div>
        <ChannelAnalytics></ChannelAnalytics>
      </div>
    </div>
  )
}

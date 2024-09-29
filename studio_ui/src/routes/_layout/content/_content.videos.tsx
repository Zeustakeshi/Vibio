import { VideoData, VideoVisibility } from '@/common/type/video.type'
import { DataTable } from '@/components/ui/data-table'
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from '@/components/ui/pagination'
import { columns } from '@/modules/content/video/columns'
import { createFileRoute } from '@tanstack/react-router'
export const Route = createFileRoute('/_layout/content/_content/videos')({
  component: Videos,
})

const videos: VideoData[] = new Array(5).fill(0).map((_, index) => ({
  video: {
    id: index.toString(),
    title:
      ' Lorem ipsum dolor sit amet consectetur adipisicing elit. Aut, odio?',
    description:
      'Lorem ipsum dolor sit amet consectetur adipisicing elit. Animi, quam ab quidem odio voluptates blanditiis qui est doloremque autem facere corporis maxime modi, minus, magnam omnis. Adipisci sunt magnam quasi nesciunt delectus fugiat vel quisquam cupiditate sint omnis? Optio, in.',
    thumbnail:
      'https://images.unsplash.com/photo-1469285994282-454ceb49e63c?q=80&w=1771&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
  },
  metadata: {
    comments: 10,
    reaction: { like: 1000, dislike: 120 },
    views: 10000,
    updatedAt: new Date(),
    createdAt: new Date(),
    visibiliity: VideoVisibility.PUBLIC,
  },
}))

function Videos() {
  return (
    <div className="">
      <DataTable columns={columns} data={videos} />
      <Pagination className="my-4">
        <PaginationContent>
          <PaginationItem>
            <PaginationPrevious href="#" />
          </PaginationItem>
          <PaginationItem>
            <PaginationLink href="#">1</PaginationLink>
          </PaginationItem>
          <PaginationItem>
            <PaginationLink href="#" isActive>
              2
            </PaginationLink>
          </PaginationItem>
          <PaginationItem>
            <PaginationLink href="#">3</PaginationLink>
          </PaginationItem>
          <PaginationItem>
            <PaginationEllipsis />
          </PaginationItem>
          <PaginationItem>
            <PaginationNext href="#" />
          </PaginationItem>
        </PaginationContent>
      </Pagination>
    </div>
  )
}

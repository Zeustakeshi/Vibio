import { Playlist, PlaylistVideo } from "@/common/type/playlist.type";
import {
    newPlaylistSchema,
    updatePlaylistSchema,
} from "@/schema/playlist.schema";
import { z } from "zod";
import { Pageable } from "../common/type/page.type";
import { api } from "../lib/api";

// studio
export const createNewPlaylist = async (
    data: z.infer<typeof newPlaylistSchema>
): Promise<Playlist> => {
    return await api.post(`channels/studio/playlists`, data);
};

export const getAllPlaylistByChannelId = async (
    page: number,
    limit: number
): Promise<Pageable<Playlist>> => {
    return await api.get(`/channels/studio/playlists`, {
        params: {
            page,
            limit,
        },
    });
};

export const getPlaylistById = async (
    playlistId: string
): Promise<Playlist> => {
    return await api.get(`/channels/studio/playlists/${playlistId}`);
};

export const updatePlaylist = async (
    playlistId: string,
    data: z.infer<typeof updatePlaylistSchema>
): Promise<Playlist> => {
    return await api.put(`/channels/studio/playlists/${playlistId}`, data);
};

export const deletePlaylist = async (playlistId: string) => {
    return api.delete(`/channels/studio/playlists/${playlistId}`);
};

export const getAllVideoByPlaylistId = async (
    playlistId: string,
    page: number
): Promise<Pageable<PlaylistVideo>> => {
    return await api.get(`channels/studio/playlists/${playlistId}/videos`, {
        params: { page, limit: 10 },
    });
};

export const addVideoToPlaylist = async (
    playlistId: string,
    videoId: string
): Promise<number> => {
    return await api.post(
        `/channels/studio/playlists/${playlistId}/videos`,
        undefined,
        {
            params: { videoId },
        }
    );
};

export const deleteVideoPlaylist = async (
    playlistId: string,
    videoId: string
): Promise<boolean> => {
    return await api.delete(`/channels/studio/playlists/${playlistId}/videos`, {
        params: { videoId },
    });
};

export const updatePlaylistVideoOrder = async (
    playlistId: string,
    videoId: string,
    newOrder: number
): Promise<number> => {
    return await api.patch(
        `channels/studio/playlists/${playlistId}/videos/order`,
        {
            newOrder,
            videoId,
        }
    );
};

// guest
export const getAllPublicPlaylistByChannelId = async (
    channelId: string,
    page: number
): Promise<Pageable<Playlist>> => {
    return await api.get(`/channels/guest/${channelId}/playlists`, {
        params: {
            page,
            limit: 10,
        },
    });
};

export const getPublicPlaylistById = async (
    playlistId: string
): Promise<Playlist> => {
    return await api.get(`/channels/guest/playlists/${playlistId}`);
};

export const getAllVideoPublicByPlaylistId = async (
    playlistId: string,
    page: number
): Promise<Pageable<PlaylistVideo>> => {
    return await api.get(`/channels/guest/playlits/${playlistId}/videos`, {
        params: { page, limit: 10 },
    });
};

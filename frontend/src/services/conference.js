import api from './api';

export const getConferences = async (params) => {
  try {
    const response = await api.get('/conferences', { params });
    return response;
  } catch (error) {
    throw error;
  }
};

export const getConferenceById = async (id) => {
  try {
    const response = await api.get(`/conferences/${id}`);
    return response;
  } catch (error) {
    throw error;
  }
};

export const createConference = async (data) => {
  try {
    const response = await api.post('/conferences', data);
    return response;
  } catch (error) {
    throw error;
  }
};

export const updateConference = async (id, data) => {
  try {
    const response = await api.put(`/conferences/${id}`, data);
    return response;
  } catch (error) {
    throw error;
  }
};

export const deleteConference = async (id) => {
  try {
    const response = await api.delete(`/conferences/${id}`);
    return response;
  } catch (error) {
    throw error;
  }
};

export const searchConferences = async (keyword) => {
  try {
    const response = await api.get('/conferences/search', {
      params: { keyword }
    });
    return response;
  } catch (error) {
    throw error;
  }
}; 
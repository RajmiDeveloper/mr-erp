const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? ''

export class ApiError extends Error {
  constructor(
    message: string,
    public readonly status: number,
  ) {
    super(message)
  }
}

export async function http<T>(path: string, options?: RequestInit): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...options?.headers,
    },
  })

  if (!response.ok) {
    let message = `No se pudo completar la solicitud (${response.status})`

    try {
      const body = (await response.json()) as { message?: string; detail?: string }
      message = body.message ?? body.detail ?? message
    } catch {
      // La respuesta de error no contiene JSON.
    }

    throw new ApiError(message, response.status)
  }

  return response.json() as Promise<T>
}

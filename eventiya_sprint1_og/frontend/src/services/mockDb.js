// This service simulates a backend API using the browser's LocalStorage.
// It allows the app to "work" (Register/Login) even without a running server.

const DB_KEY = 'eventiya_mock_users';

const getLocalUsers = () => {
    const users = localStorage.getItem(DB_KEY);
    return users ? JSON.parse(users) : [];
};

const saveLocalUsers = (users) => {
    localStorage.setItem(DB_KEY, JSON.stringify(users));
};

export const mockDb = {
    register: async (userData) => {
        // Simulate network delay
        await new Promise(resolve => setTimeout(resolve, 800));

        const users = getLocalUsers();
        if (users.find(u => u.email === userData.email)) {
            throw { response: { status: 409, data: { message: 'User already exists' } } };
        }

        users.push({
            ...userData,
            id: Date.now(),
            createdAt: new Date().toISOString()
        });

        saveLocalUsers(users);
        return { status: 201, data: { message: 'User created' } };
    },

    login: async (credentials) => {
        // Simulate network delay
        await new Promise(resolve => setTimeout(resolve, 800));

        const users = getLocalUsers();
        const user = users.find(u => u.email === credentials.email && u.password === credentials.password);

        if (!user) {
            throw { response: { status: 401, data: { message: 'Invalid credentials' } } };
        }

        // Simulate a JWT token (header.payload.signature)
        const header = btoa(JSON.stringify({ alg: "HS256", typ: "JWT" }));
        const payload = btoa(JSON.stringify({
            userId: user.id,
            sub: user.email,
            role: user.role,
            exp: Math.floor(Date.now() / 1000) + (60 * 60 * 24) // 24 hours
        }));
        const signature = "dummy_signature";
        const token = `${header}.${payload}.${signature}`;

        return { data: { token, user } };
    }
};

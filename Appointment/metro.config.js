/**
 * Metro configuration for React Native
 * https://facebook.github.io/metro/docs/en/configuration
 *
 * @format
 */
const { getDefaultConfig } = require('@react-native/metro-config');

const config = getDefaultConfig(__dirname);

module.exports = {
  ...config,
  resolver: {
    ...config.resolver,
    // remove any additional invalid resolver options here
  },
  transformer: {
    ...config.transformer,
    // remove any additional invalid transformer options here
  },
  serializer: {
    ...config.serializer,
    // remove any additional invalid serializer options here
  },
  server: {
    ...config.server,
    // Remove invalid server options
    // forwardClientLogs: true, // Remove this line
  },
  watcher: {
    ...config.watcher,
    // Remove invalid watcher options
    // unstable_workerThreads: false, // Remove this line
  },
};

const path = require('path');
var webpack = require("webpack");

module.exports = {
  entry: './src/embed.ts',
  mode: 'production',
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/
      }
    ]
  },
  resolve: {
    extensions: [ '.tsx', '.ts', '.js' ],
    alias: {
      'express-handlebars': 'handlebars/dist/handlebars.js'
    }
  },
  node: {
      fs: 'empty'
  },
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist')
  }
};

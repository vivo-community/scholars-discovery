const path = require('path');
const CopyPlugin = require('copy-webpack-plugin');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

module.exports = {
  entry: {
    'scholars-embed.min': './src/index.ts',
    'vendor-bundle.min': [
      path.resolve(__dirname, './src/vendor/badge.js'),
      path.resolve(__dirname, './src/vendor/embed.js'),
      'jquery',
      'popper.js',
      'bootstrap'
    ]
  },
  mode: 'production',
  devtool: 'source-map',
  output: {
    filename: '[name].js',
    path: path.resolve(__dirname, '../resources/static/embed')
  },
  optimization: {
    minimizer: [
      new UglifyJsPlugin({
        cache: true,
        parallel: true,
        uglifyOptions: {
          compress: false,
          ecma: 6,
          mangle: true
        },
        sourceMap: true,
        include: /\.min\.js$/,
      })
    ]
  },
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
    extensions: ['.ts', '.tsx', '.js']
  },
  plugins: [
    new CopyPlugin([
      {
        from: './node_modules/bootstrap/dist/css/bootstrap.min.css',
        to: './bootstrap.min.css'
      },
      {
        from: './node_modules/bootstrap/dist/css/bootstrap.min.css.map',
        to: './bootstrap.min.css.map'
      }
    ]),
  ]
};


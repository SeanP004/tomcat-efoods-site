'use strict';

module.exports = function(grunt) {

    grunt.initConfig({

        concat: {
            all: {
                options: { separator: ';' },
                src: ['js/*.js'],
                dest: '../WebContent/assets/js/main.js'
            }
        },

        copy: {
            all: {
                files: [
                  { expand: true,
                    cwd: 'fonts/',
                    src: ['**'],
                    dest: '../WebContent/assets/fonts' },
                  { expand: true,
                    cwd: 'libs/bootstrap/fonts/',
                    src: ['**'],
                    dest: '../WebContent/assets/fonts' },
                  { expand: true,
                    cwd: 'img/',
                    src: ['**'],
                    dest: '../WebContent/assets/img' }
                ]
            }
        },

        less: {
            all: {
                files: {
                    "../WebContent/assets/css/main.css": "less/main.less"
                }
            }
        },

        watch: {
            source: {
                options: {
                    atBegin: true
                },
                files: [
                    'js/*.js',
                    'less/*.less'
                ],
                tasks: ['build']
            },
            assets: {
                files: ['fonts/**', 'img/**'],
                tasks: ['copy']
            }
        }

    });

    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-contrib-concat');

    grunt.registerTask('dev', ['watch']);
    grunt.registerTask('default', ['build']);
    grunt.registerTask('build', [
        'concat',
        'less',
        'copy'
    ]);

};

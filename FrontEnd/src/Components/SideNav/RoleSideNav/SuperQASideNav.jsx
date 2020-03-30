import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';

/**
 * Komponen SideNav untuk Super QA
 */
export default class SuperQASideNav extends Component {
    render() {
        return (
            <SideNavFramework
                links={
                    [
                        {
                            link: "#",
                            title: "Halaman Utama",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Hasil Pemeriksaan",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Tugas Pemeriksaan",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Rekomendasi",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Bukti Rekomendasi",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Dashboard",
                            active: false
                        }
                    ]
                }
            />
        );
    }
}

import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';

export default class AdministratorSideNav extends Component {
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
                            title: "Pengguna",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Kantor Cabang",
                            active: false
                        }
                    ]
                }
            />
        );
    }
}
